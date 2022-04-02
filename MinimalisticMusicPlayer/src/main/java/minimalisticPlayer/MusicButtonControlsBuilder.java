package minimalisticPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import java.io.File;

/**
 * This class builds the {@link MusicButtonControls} backend class according to the specified input.
 * At program start, this class will be called to create a new MusicButtonControls class, which happens with the
 * {@link MusicButtonControlsBuilder#build()} method. When a valid file was chosen with the JFileChooser, then @TODO a method in the GUI will build another MusicButtonControls...
 */
public class MusicButtonControlsBuilder {

    private final String SUCCESS_MESSAGE = "File successfully retrieved - compatible with audio system.";
    private final String FAILURE_MESSAGE = "This audio format is not supported by the audio system.";
    private boolean isPlaying; // indicate whether file is being played in a thread at the moment
    private boolean isPaused; // indicate whether a played file is set to "pause" mode
    private long fileByteLength;
    private long bytesReadFromFile;
    private Mixer mixer;
    private AudioInputStream myStream;
    private File file;
    private SourceDataLine bufferLine;
    private FileHandler handler; // @TODO: should be final, i.e. may not be changed, but so far, is in conflict with constructor...
    private int stateVariable; // depending on which constructor was called, build method is implemented differently

    public MusicButtonControlsBuilder() {
        isPlaying = false;
        isPaused = false;
        fileByteLength = 0;
        bytesReadFromFile = 0;
        mixer = null;
        myStream = null;
        file = null;
        bufferLine = null;
        handler = new FileHandler();
        stateVariable = 0;
    }

    public MusicButtonControlsBuilder(FileHandler handler) {
        this();
        this.handler = handler;
    }

    public MusicButtonControls build() {
        if (stateVariable == 0) {
            stateVariable+=1;
            return new MusicButtonControls(file, mixer, myStream, bufferLine, fileByteLength);
        } else {
            file = handler.getMusicFile();
            AudioSystemProvider provider = new AudioSystemProvider(file);
            if (!provider.isSupported) {
                file = null;
            } else {
                mixer = provider.getMyMixer();
                myStream = provider.getMyStream();
                bufferLine = provider.getMyLine();
                fileByteLength = file.length();
                isPlaying = false;
                isPaused = false;
                bytesReadFromFile = 0;
            }
            return new MusicButtonControls(file, mixer, myStream, bufferLine, fileByteLength);
        }
    }
}
