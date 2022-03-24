package com.javasynthv1;


import javax.sound.sampled.*;

public class MyPlayerClass {
    public static void main(String[] args) {
        MicroRecorder recorder = new MicroRecorder();
        recorder.record("outfile.wav",1000);


/*        ClipPlayer player1 = new ClipPlayer();
        player1.player();

        BufferPlayer player2 = new BufferPlayer();

        DataLine.Info info = null;
        Mixer mixer = player2.getMixer();
        try {
            info =  (DataLine.Info) mixer.getSourceLineInfo()[1];
        }
        catch (Exception e) {

        }

        System.out.println("Supported formats:");
        for (AudioFormat format: info.getFormats()) {
            System.out.println(format);
        }


        player2.player();

 */
    }
}
