package minimalisticPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Provides functionality for the buttons in the {@link MinimalisticPlayerFrontend} GUI
 */
public class MusicButtonControls {

    private boolean isPlaying; // indicate whether file is being played in a thread at the moment
    private boolean isPaused; // indicate whether a played file is set to "pause" mode
    private long fileByteLength;
    private long bytesReadFromFile;
    private long totalFramesRead;
    private Mixer mixer;
    private AudioInputStream myStream;
    private File file;
    private SourceDataLine bufferLine;
    private Thread musicPlayingThread;
    private MinimalisticPlayerFrontend frontend;

    MusicButtonControls(File file, Mixer mixer, AudioInputStream myStream, SourceDataLine bufferLine, long fileByteLength, MinimalisticPlayerFrontend frontend) {
        this.file = file;
        this.mixer = mixer;
        this.myStream = myStream;
        this.bufferLine = bufferLine;
        this.fileByteLength = fileByteLength;
        this.frontend = frontend;
        isPlaying = false;
        isPaused = false;
            }

    public Mixer getMixer() {
        return mixer;
    }

    public void setMixer(Mixer mixer) {
        this.mixer = mixer;
    }

    public AudioInputStream getMyStream() {
        return myStream;
    }

    public void setMyStream(AudioInputStream myStream) {
        this.myStream = myStream;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public SourceDataLine getBufferLine() {
        return bufferLine;
    }

    public void setBufferLine(SourceDataLine bufferLine) {
        this.bufferLine = bufferLine;
    }

    public synchronized void stop() {
        isPlaying = false;
        bytesReadFromFile = 0;
        bufferLine.drain();
        bufferLine.flush();
        bufferLine.close();
    }
    public synchronized void pause() {
        if (!isPaused) {
            isPaused = true;
        }
        else {
            isPaused = false;
        }
    }

    public synchronized void start(){
        if (!isPlaying) {
            isPlaying = true;
            try {
                bufferLine.open(myStream.getFormat());
            } catch (LineUnavailableException e) {
                System.out.println(e.getMessage());
            }
            if (myStream==null) {
                System.out.println("No files loaded!");
                return;
            }
            try {
                this.myStream = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Playing set true.");
            System.out.println(fileByteLength);
            Runnable runnable = new Runnable() {
                final int chunkSize = 1024;
                int numBytesRead = 0; // bytes read from last chunk grabbing
                byte[] b = new byte[chunkSize];

                public void run() {
                    bufferLine.start();
                    totalFramesRead = 0;
                    while (isPlaying && bytesReadFromFile < fileByteLength) {
                        while (isPaused) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        try {
                            numBytesRead = myStream.read(b, 0, chunkSize);
                            totalFramesRead = totalFramesRead + numBytesRead/(int)myStream.getFormat().getFrameSize();
                        } catch (IOException e) {
                            System.out.println(e.getMessage() + "...ERROR while trying to read audio file.");
                        }
                        if (numBytesRead == -1) {
                            System.out.println("End of file reached! Nothing was read.");
                            bufferLine.drain();
                            bufferLine.close();
                            break;
                        }
                        bytesReadFromFile += numBytesRead;
                        bufferLine.write(b, 0, numBytesRead);
                        frontend.slider.setValue((int)bytesReadFromFile);
                    }
                }
            };
            musicPlayingThread = new Thread(runnable);
            musicPlayingThread.start();
        } else {
            System.out.println("Already set to play. Possibly paused.");
        }
    }
}
