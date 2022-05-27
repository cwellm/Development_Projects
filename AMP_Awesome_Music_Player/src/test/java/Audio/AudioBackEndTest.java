package Audio;

import Logging.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import javax.sound.sampled.AudioInputStream;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AudioBackEndTest {
    @ParameterizedTest
    @MethodSource("provideValidFilesForTests")
    void testIsFileFormatSupported(File file) {
        AudioBackEnd backend = new AudioBackEnd(new Logger());
        backend.setFile(file);
        // System.out.println(file.getAbsolutePath());
        assertTrue(backend.setIfFileFormatSupported());
    }
    @Test
    void testNotIsFileFormatSupported() {
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(new File("src/test/resources/png_example.png"));
        assertTrue(!backend.setIfFileFormatSupported());
        Mockito.verify(mockedLogger).writeLog(Mockito.anyString());
    }

    @ParameterizedTest
    @MethodSource("provideValidFilesForTests")
    void testIsAudioInputLineAvailable(File file) {
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(file);
        backend.setIfFileFormatSupported();
        System.out.println(file.getAbsolutePath());
        assertTrue(backend.setIfAudioInputLineAvailable());
    }

    @Test
    void testIsNotAudioInputLineAvailable() {
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(new File("src/test/resources/wav_example.wav"));
        Mockito.verify(mockedLogger).writeLog(Mockito.anyString());
    }

    @Test
    void testGetStreamForFile() {
        File file = new File("src/test/resources/wav_example.wav");
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(file);
        backend.setIfFileFormatSupported();
        try {
            assertEquals(backend.getStreamForFile().getClass(),AudioInputStream.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNullGetStreamForFile() {
        File file = new File("src/test/resources/wav_example.wav");
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        try {
            backend.getStreamForFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(mockedLogger).writeLog(Mockito.anyString());
    }

    @Test
    void testErrorGetStreamForFile() {
        File file = new File("src/test/resources/wav_example.wav");
        File wrongFormatFile = new File("src/test/resources/png_example.png");
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(file);
        backend.setIfFileFormatSupported();
        backend.setFile(wrongFormatFile);
        Exception e = assertThrows(IOException.class, () -> {backend.getStreamForFile();});
        Mockito.verify(mockedLogger).writeLog(Mockito.anyString());
    }

    @Test
    void checkForNonNull() {
        File file = new File("src/test/resources/wav_example.wav");
        Logger mockedLogger = Mockito.mock(Logger.class);
        AudioBackEnd backend = new AudioBackEnd(mockedLogger);
        backend.setFile(file);
        backend.setIfFileFormatSupported();
        assertNotNull(backend.getAudioFileFormat());
        try {
            assertNotNull(backend.getStreamForFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assumeTrue(backend.getAudioFileFormat() != null);
        backend.setIfAudioInputLineAvailable();
        assertNotNull(backend.getLineForFile());
    }

    private static Stream<Arguments> provideValidFilesForTests() {
        return Stream.of(
                Arguments.of(new File("src/test/resources/wav_example.wav")),
                Arguments.of(new File("src/test/resources/aiff_example.aiff")),
                Arguments.of(new File("src/test/resources/au_example.au")),
                Arguments.of(new File("src/test/resources/mp3_example.mp3"))
        );
    }
}