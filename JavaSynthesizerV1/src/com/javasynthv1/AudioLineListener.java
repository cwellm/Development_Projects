package com.javasynthv1;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioLineListener implements LineListener {

    @Override
    public void update(LineEvent lineEvent) {
        System.out.println("LINE LISTENER SAYS:");
        System.out.println(lineEvent.toString());
    }
}
