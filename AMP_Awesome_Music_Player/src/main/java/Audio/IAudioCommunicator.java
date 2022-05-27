package Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

public interface IAudioCommunicator {
    boolean setIfFileFormatSupported();
    boolean setIfAudioInputLineAvailable();
    boolean setIfAudioOutputLineAvailable();

    AudioInputStream getStreamForFile() throws IOException;
    SourceDataLine getLineForFile();
}
