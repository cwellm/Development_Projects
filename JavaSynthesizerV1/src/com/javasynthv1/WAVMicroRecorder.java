package com.javasynthv1;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WAVMicroRecorder extends AbstractRecorder {
    long currentTime = 0;
    long prevTime = 0;
    long totalElapsed = 0;
    AudioInputStream myStream;
    AudioFileFormat myFormat;
    WAVMicroRecorder() {
        super();
        try {
            myFormat = AudioSystem.getAudioFileFormat(file);
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    WAVMicroRecorder(AudioFormat format) {
        super(format);
    }

    @Override
    public void record(String fileName, long time) {
        File outfile = new File(fileName);
        FileOutputStream outStream = null;

        try {
            outfile.createNewFile();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        try {
            outStream = new FileOutputStream(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            myStream = new AudioInputStream(line);
            line.open(format);
            byte[] byteBuffer = new byte[line.getBufferSize()/4];
            int numBytesRead = 0;
            line.start();
            System.out.println("Successfully started.");
/*            while (totalElapsed<time) {
                prevTime = System.currentTimeMillis();
                //numBytesRead = line.read(byteBuffer,0,byteBuffer.length);
                try {
                    numBytesRead = myStream.read(byteBuffer, 0, byteBuffer.length);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                currentTime = System.currentTimeMillis();
                totalElapsed += currentTime - prevTime;
            }
            System.out.println("Ended recording. Now start writing to file.");*/
            try {
                AudioSystem.write(myStream, myFormat.getType(), new File(fileName));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (LineUnavailableException ex) {
            System.out.println("This line is currently unavailable.");
            System.exit(1);
        } finally {
            if (!(line == null)) {
                line.drain();
                line.close();
            }
        }
    }
}
