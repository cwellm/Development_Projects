package minimalisticPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;

/**
 * Provides the necessary parts of the audio system in order to play back audio files in various formats.
 * This includes a Mixer (take audio input and send it to output port), a suitable DataLine (here, it will be a
 * SourceDataLine), and the audio stream. A suitable audio file needs to be provided in order to open the correct
 * line. Whether the audio format is supported is checked by {@link AudioCompatibilityChecker}.
 */
public class AudioSystemProvider {
    public boolean isSupported;

    private Mixer myMixer;
    private AudioInputStream myStream;
    private SourceDataLine myLine;

    AudioSystemProvider(File file) {
        AudioCompatibilityChecker checker = new AudioCompatibilityChecker();
        try {
            isSupported = checker.checkCompatibility(file);
            this.myMixer = checker.getMyMixer();
            this.myStream = checker.getMyStream();
            this.myLine = checker.getMyLine();
            this.myLine.addLineListener(new AudioLineListener());
        } catch (UnsupportedAudioFileException e) {
            isSupported = false;
        }
    }

    AudioSystemProvider() {
        this(null);
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

}
