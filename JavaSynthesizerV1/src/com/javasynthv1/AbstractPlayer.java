package com.javasynthv1;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractPlayer {
    Mixer mixer;
    AudioInputStream myStream;
    File file = new File("SingingBowl.wav");
    protected AudioLineListener listener;

    public AbstractPlayer() {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
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
    }

    public abstract void player();
    public Mixer getMixer() {
        return mixer;
    }
}
