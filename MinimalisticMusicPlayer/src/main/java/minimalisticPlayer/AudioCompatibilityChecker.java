package minimalisticPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Checks whether a desired audio file taken for playback possesses an audio (file) format which can be handled by
 * the audio system
 */
public class AudioCompatibilityChecker {

    private Mixer myMixer;
    private AudioInputStream myStream;
    private SourceDataLine myLine;

    AudioCompatibilityChecker() {
        myMixer = null;
        myStream = null;
        myLine = null;
    }

    public Mixer getMyMixer() {
        return myMixer;
    }

    public AudioInputStream getMyStream() {
        return myStream;
    }

    public SourceDataLine getMyLine() {
        return myLine;
    }

    boolean checkCompatibility(File file) throws UnsupportedAudioFileException {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info: infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            try {
                myLine = AudioSystem.getSourceDataLine(AudioSystem.getAudioFileFormat(file).getFormat(), info);
                myMixer = mixer;
                myStream = AudioSystem.getAudioInputStream(file);
                return true;
            } catch (IOException e) {
                System.out.println(e.getMessage() + "...Audio System cannot check audio format of file.");
                continue;
            } catch (LineUnavailableException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        System.out.println("AUDIO FORMAT OF FILE NOT SUPPORTED!");
        return false;
    }
}
