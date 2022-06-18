package Executor.SaveScreen;

import FileUtils.JPlaylist;
import GUI.SaveScreen.SaveScreenGUI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SaveScreenGUIExecutorV1Test {

    @Test
    void testSaveFile(@TempDir Path tempDir) {
        final String PLAYLIST_NAME = "filename";
        JPlaylist jPlaylist = Mockito.spy(JPlaylist.class);
        SaveScreenGUI gui = Mockito.mock(SaveScreenGUI.class);

        // todo path independent of OS...
        File f1 = tempDir.resolve("audio/file.wav").toFile();
        File f2 = tempDir.resolve("another/audio/file.mp3").toFile();
        File f3 = tempDir.resolve("my/audio/file.boo").toFile();

        SaveScreenGUIExecutorV1 executor = new SaveScreenGUIExecutorV1(jPlaylist);
        executor.setGUI(gui);

        Mockito.when(gui.getJFileChooser()).thenReturn(Mockito.mock(JFileChooser.class));
        Mockito.when(gui.getJFileChooser().getCurrentDirectory()).thenReturn(tempDir.toFile());
        Mockito.when(gui.getTextField()).thenReturn(Mockito.mock(JTextField.class));
        Mockito.when(gui.getTextField().getText()).thenReturn(PLAYLIST_NAME);

        jPlaylist.addToPlaylist(f1);
        jPlaylist.addToPlaylist(f2);
        jPlaylist.addToPlaylist(f3);

        executor.saveFile();

        // Was correct file created?
        assertTrue(Files.exists(Path.of(tempDir.toString(), PLAYLIST_NAME + ".amp42")));

        // Check the contents
        try {
            ArrayList<String> filePaths =
                    (ArrayList<String>) Files.readAllLines(Path.of(tempDir.toString(), PLAYLIST_NAME + ".amp42"));
            for (int i = 0; i< jPlaylist.getPlaylist().size(); i++) {
                assertEquals(jPlaylist.getPlaylist().get(i).getAudioFile().getAbsolutePath(),
                        filePaths.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}