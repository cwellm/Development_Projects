package Audio;

import Logging.Logger;
import lombok.NonNull;
import lombok.Getter;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import java.io.File;
import java.io.IOException;

public class AudioBackEnd implements IAudioCommunicator {

    @Getter
    private File file;
    private Logger logger;
    private SourceDataLine sourceDataLine;
    private TargetDataLine targetDataLine;
    @Getter
    private AudioFileFormat audioFileFormat;
    public AudioBackEnd(@NonNull Logger logger) {
        file = null;
        this.logger = logger;
        sourceDataLine = null;
        targetDataLine = null;
        audioFileFormat = null;
    }

    @Override
    public boolean setIfFileFormatSupported() {
        try {
            audioFileFormat = AudioSystem.getAudioFileFormat(file);
            return true;
        }
        catch (UnsupportedAudioFileException e) {
            logger.writeLog("This audio file format is not supported, or the file is not a valid" +
                    "audio file.");
            return false;
        }
        catch (IOException e) {
            logger.writeLog("An error occured while trying to load the file.");
            return false;
        }
    }

    @Override
    public boolean setIfAudioInputLineAvailable() {
        try {
            sourceDataLine = AudioSystem.getSourceDataLine(audioFileFormat.getFormat());
            return true;
        } catch (LineUnavailableException e) {
            logger.writeLog("There are currently no audio resources available to play the file.");
        }
        return false;
    }

    @Override
    public boolean setIfAudioOutputLineAvailable() {
        try {
            targetDataLine = AudioSystem.getTargetDataLine(audioFileFormat.getFormat());
            return true;
        } catch (LineUnavailableException e) {
            logger.writeLog("There are currently no audio resources available to play the file.");
        }
        return false;
    }

    @Override
    /**
     * returns an audio input stream of the audio file, if the file possesses a valid audio format; it may return null
     * if the audioFileFormat is still null (no valid file has been loaded); throws an exception if the audio file is
     * not supported
     */
    public AudioInputStream getStreamForFile() throws IOException {
        if (audioFileFormat != null) {
            try {
                return AudioSystem.getAudioInputStream(file);
            }
            catch (UnsupportedAudioFileException | IOException e) {
                logger.writeLog("Error while trying to open an input stream from the file.");
                throw new IOException();
            }
        }
        else {
            logger.writeLog("It seems that no audio file has been loaded for the AudioBackend yet.");
            return null;
        }
    }

    @Override
    @NonNull public SourceDataLine getLineForFile() {
        return sourceDataLine;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @NonNull Logger getLogger() {
        return logger;
    }
}
