package com.javasynthv1;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractRecorder {
    // protected Mixer mixer;
    protected AudioLineListener listener;
    protected TargetDataLine line;
    protected AudioFormat format;
    protected DataLine.Info dataLineInfo;
    protected File file;

    AbstractRecorder() {
        file = new File("SingingBowl.wav");
        try {
            this.format = AudioSystem.getAudioFileFormat(file).getFormat();
        }
        catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported Audio Format.");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Some problem with the file.");
            System.exit(1);
        }
        dataLineInfo = new DataLine.Info(TargetDataLine.class,this.format);
    }

    AbstractRecorder(AudioFormat format) {
        this.format = format;
        dataLineInfo = new DataLine.Info(TargetDataLine.class, this.format);
        if (!AudioSystem.isLineSupported(dataLineInfo)) {
            System.out.println("This audio format is not supported by the system!");
            System.exit(1);
        }
    }
    public abstract void record(String fileName, long time);
}
