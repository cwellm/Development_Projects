package P_Practices;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ThreadedControls {

    private Mixer mixer;
    private AudioInputStream myStream;
    private File file = null;
    private AudioLineListener listener;
    private Mixer.Info[] mixerInfo;
    private SourceDataLine bufferLine = null;
    private boolean isPlay = false;
    private int lengthToRead;
    private int totalRead;
    private Thread playingFile;
    private boolean isPause = false;

    ThreadedControls(String inputFile) {
        lengthToRead = 5000000;// (int) file.length();
        totalRead = 0;
        file = new File(inputFile);
        mixerInfo = AudioSystem.getMixerInfo();
        listener = new AudioLineListener();
        for (Mixer.Info i : mixerInfo) {
            System.out.println("MIXER");
            System.out.println("Name: " + i.getName());
            System.out.println("Vendor: " + i.getVendor());
            System.out.println("Version: " + i.getVersion());
            System.out.println("Description: " + i.getDescription());
            System.out.println();
        }

        for (int j = 0; j < mixerInfo.length; j++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[j]);
            System.out.println("Chosen mixer info, Mixer NO " + j + ":");
            Line.Info[] sourceInfo = mixer.getSourceLineInfo();
            Line.Info[] targetInfo = mixer.getTargetLineInfo();

            int a = 0;
            for (Line.Info i : sourceInfo) {
                System.out.println("Line No. " + a++);
                System.out.println(i);
                System.out.println();
            }
            a = 0;
            for (Line.Info i : targetInfo) {
                System.out.println("Line No. " + a++);
                System.out.println(i);
                System.out.println();
            }

            System.out.println("SUPPORTED AUDIO FILE TYPES");
            for (AudioFileFormat.Type i : AudioSystem.getAudioFileTypes()) {
                System.out.println("Type: " + i.toString());
                //System.out.println("Format: " + i.getFormat());
                System.out.println("");
            }
        }

        //myStream = null;
        try {
            this.myStream = AudioSystem.getAudioInputStream(file);
            System.out.println("File successfully retrieved.");
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }

        this.mixer = AudioSystem.getMixer(mixerInfo[1]);
        System.out.println("Mixer 1 retrieved successfully.");

        // open bufferline
        try {
            bufferLine = (SourceDataLine) mixer.getLine(mixer.getSourceLineInfo()[0]);
            bufferLine.addLineListener(listener);
            bufferLine.open(AudioSystem.getAudioFileFormat(file).getFormat());
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void stop() {
        isPlay = false;
        bufferLine.drain();
        bufferLine.flush();
        try {
            this.myStream = AudioSystem.getAudioInputStream(file);
            System.out.println("File successfully retrieved.");
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public synchronized void pause() {
        if (!isPause) {
            isPause = true;
        }
        else {
            isPause = false;
        }
    }

    public void start(){
        Runnable runnable = new Runnable() {
            int chunkSize = 1024;
            int numBytesRead = 0;
            byte[] b = new byte[chunkSize];
            public void run() {
                bufferLine.start();
                isPlay = true;
                while (isPlay && totalRead<lengthToRead) {
                    while (isPause) {
                        try {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    try {
                        numBytesRead = myStream.read(b, 0, chunkSize);
                    }
                    catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    if (numBytesRead == -1) {
                        System.out.println("End of file reached?");
                        break;
                    }
                    totalRead += numBytesRead;
                    bufferLine.write(b, 0, numBytesRead);
                }
            }
        };
        playingFile = new Thread(runnable);
        playingFile.start();
    }
}
