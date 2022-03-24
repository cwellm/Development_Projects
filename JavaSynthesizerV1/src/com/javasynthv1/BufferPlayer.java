package com.javasynthv1;

import javax.sound.sampled.*;
import java.io.IOException;

public class BufferPlayer extends AbstractPlayer {
    SourceDataLine bufferLine = null;

    BufferPlayer() {
        super();
        try {
            bufferLine = (SourceDataLine) mixer.getLine(mixer.getSourceLineInfo()[0]);
            bufferLine.addLineListener(listener);
        }
        catch (LineUnavailableException e) {
            System.out.println("BufferPlayer: " + e.getMessage());
        }
        System.out.println("Format:" + bufferLine.getFormat());

        System.out.println("SUPPORTED Audio formats: ");
        System.out.println(mixer.getSourceLineInfo()[0].toString());

        try {
            AudioFileFormat format = AudioSystem.getAudioFileFormat(file);
            System.out.println("Audio file format of file:" + format);
        }
        catch (UnsupportedAudioFileException e) {
            System.out.println("BufferPlayer:" + e.getMessage());
        }
        catch (IOException e) {

        }
    }

    @Override
    public void player() {
        try {
            bufferLine.open(AudioSystem.getAudioFileFormat(file).getFormat());
            bufferLine.start();
            int lengthToRead = 500000;
            int chunkSize = 1024;
            int numBytesRead = 0;
            int totalRead = 0;
            System.out.println("BufferLine successfully opened.");
            byte[] b = new byte[chunkSize];
            while (totalRead<lengthToRead) {
                numBytesRead = myStream.read(b,0,chunkSize);
                if (numBytesRead==-1) {
                    System.out.println("End of file reached?");
                    break;
                }
                totalRead+=numBytesRead;
                bufferLine.write(b,0,numBytesRead);
            }
            System.out.println("All data read from stream.");
            System.out.println("Total number of bytes read is " + totalRead);
        }
        catch (Exception e) {
            System.out.println("ERROR!" + e.getMessage());
        }
        finally {
            bufferLine.close();
        }
    }
}
