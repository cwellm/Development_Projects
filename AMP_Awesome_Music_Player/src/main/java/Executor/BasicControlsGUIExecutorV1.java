package Executor;

import Audio.AudioBackEnd;
import Controller.IBasicControlsDispatcher;
import Controller.IBasicControlsGUIController;
import Logging.Logger;
import lombok.NonNull;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.io.IOException;

public class BasicControlsGUIExecutorV1 implements IBasicControlsGUIController, Runnable, IBasicControlsDispatcher {

    private static final int READING_BYTE_SIZE = 1024;
    private final AudioBackEnd audioBackEnd;
    private final Logger logger;
    private AudioInputStream audioInputStream;
    private SourceDataLine playDataLine;

    private boolean isPlay;
    private boolean isPause;
    private boolean isStop;

    private long byteAudioFileSize;
    private long playingPosition;
    private long bytesRead;

    private byte[] currentByteBuffer;

    private ControllerState controllerState;

    public BasicControlsGUIExecutorV1(AudioBackEnd backEnd, Logger logger) {
        this.audioBackEnd = backEnd;
        this.logger = logger;
        isPlay = false;
        isPause = false;
        isStop = false;
        audioInputStream = null;
        playDataLine = null;
        byteAudioFileSize = 0;
        playingPosition = 0;
        bytesRead = 0;
        currentByteBuffer = new byte[READING_BYTE_SIZE];
        controllerState = ControllerState.RESTING;
    }

    @Override
    public synchronized void startAction() {
        isPause = false;
        isStop = false;
        if (!isPlay && (audioBackEnd.getAudioFileFormat()!=null)) {
            isPlay = true;
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public synchronized void stopAction() {
        isStop = true;
        isPause = false;
        isPlay = false;

    }

    @Override
    public synchronized void pauseAction() {
        if (isStop) {
            return;
        }

        if (!isPause) {
            isPause = true;
        } else {
            isPause = false;
        }
    }

    @Override
    public @NonNull ControllerState getcontrollerState() {
        return this.controllerState;
    }

    public void run() {
        try {
            audioInputStream = audioBackEnd.getStreamForFile();
            byteAudioFileSize = audioInputStream.getFormat().getFrameSize()*audioInputStream.getFrameLength();
        }
        catch (IOException e) {
            // todo: do something here
        }
        if (audioInputStream == null) {
            System.out.println("Inputstream NULL");
            // todo: do something if no file loaded
        }

        playDataLine = audioBackEnd.getLineForFile();
        try {
            playDataLine.open(audioInputStream.getFormat());
        } catch (LineUnavailableException e) {
            // todo do something
        }
        playDataLine.start();
        controllerState = ControllerState.PLAYING;

        while (isPlay) {
            while (isPause) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    // todo some error handling
                }
            }

            try {
                bytesRead = audioInputStream.read(currentByteBuffer, 0, READING_BYTE_SIZE);

                if (bytesRead == -1) {
                    break;
                }

                playingPosition += bytesRead;

            } catch (IOException e) {
                // todo: do something
            }

            playDataLine.write(currentByteBuffer, 0, (int) bytesRead);
        }

        playDataLine.drain();
        playDataLine.close();
        controllerState = ControllerState.RESTING;
        try {
            audioInputStream.close();
        } catch (IOException e) {
            // todo handle
        }
    }

    @Override
    public void triggerSongPlay(String filePath) {
        audioBackEnd.setFile(new File(filePath));
        if (audioBackEnd.setIfFileFormatSupported() && audioBackEnd.setIfAudioInputLineAvailable()) {
            startAction();
        }
    }

    @Override
    public void triggerSongStop() {
        stopAction();
    }
}
