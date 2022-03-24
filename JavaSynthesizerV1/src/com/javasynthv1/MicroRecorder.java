package com.javasynthv1;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.*;

public class MicroRecorder extends AbstractRecorder {
    long currentTime = 0;
    long prevTime = 0;
    long totalElapsed = 0;
    MicroRecorder() {
        super();
    }
    MicroRecorder(AudioFormat format) {
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
            line.open(format);
            byte[] byteBuffer = new byte[line.getBufferSize()/4];
            int numBytesRead;
            line.start();
            while (totalElapsed<time) {
                prevTime = System.currentTimeMillis();
                numBytesRead = line.read(byteBuffer,0,byteBuffer.length);
                try {
                    outStream.write(byteBuffer, 0, numBytesRead);
                }
                catch (IOException e) {
                    System.out.println("WARNING: Something wrong with writing recorded data to file!");
                }
                currentTime = System.currentTimeMillis();
                totalElapsed += currentTime - prevTime;
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
