package com.javasynthv1;


import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.List;

public class MyPlayerClass {
    public static void main(String[] args) {

        List<Line.Info> lineInfo = new ArrayList<>();
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (Mixer.Info info: mixerInfo) {
            System.out.println("MIXER");
            Line.Info[] mixerLineInfo = AudioSystem.getMixer(info).getSourceLineInfo();
            for (Line.Info infos: mixerLineInfo) {
                System.out.println(infos.toString());
            }
            System.out.println("");
        }

        WAVMicroRecorder recorder = new WAVMicroRecorder();
        recorder.record("wav_outfile.wav",1000);
        // MicroRecorder recorder = new MicroRecorder();
        // recorder.record("outfile.wav",1000);

        // BufferPlayer player = new BufferPlayer();
        // player.player();


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
