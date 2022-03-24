package com.javasynthv1;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import java.io.IOException;

public class ClipPlayer extends AbstractPlayer {
    ClipPlayer() {
        super();
    }
    @Override
    public void player() {
        Clip myClip = null;
        System.out.println("Player started");
        try {
            System.out.println("INFO: " + mixer.getSourceLineInfo());
            myClip = (Clip) mixer.getLine(mixer.getSourceLineInfo()[1]);
            System.out.println("Source line info check");
            myClip.open(myStream);
            myClip.start();
            System.out.println("Stream started successfully.");
            myClip.addLineListener(listener);
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println("Clip error: " + e.getMessage());
            }
        }
        catch (LineUnavailableException e) {
            System.out.println("Line unavailable...ERROR!..." + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Clip IOException: " + e.getMessage());
        }
        finally {
            if (myClip != null) {
                myClip.stop();
                myClip.close();
            }
        }
    }
}
