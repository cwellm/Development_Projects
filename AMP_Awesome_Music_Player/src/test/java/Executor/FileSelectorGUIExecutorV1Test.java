package Executor;

import Audio.AudioBackEnd;
import Controller.SaveScreen.ISaveScreenGUIController;
import FileUtils.Entries;
import FileUtils.JPlaylist;
import GUI.Globals;
import GUI.SaveScreen.SaveScreenGUI;
import Logging.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSelectorGUIExecutorV1Test {

    @Nested
    @DisplayName("LoadPlaylistAndLoadFileTests")
    class LoadPlaylistTests {

        ArrayList<Path> playlistPaths;
        File tempPlaylistFile;
        JList jList;
        Logger mockedLogger;
        JFileChooser mockedChooser;
        FileSelectorGUIExecutorV1 executor;
        @BeforeEach
        void setup(@TempDir Path tempDir, @TempDir Path tempPlaylistDir) {
            playlistPaths = new ArrayList<>();
            playlistPaths.add(tempDir.resolve("firstSong.wav"));
            playlistPaths.add(tempDir.resolve("path/to/my/secondSong.wav"));
            playlistPaths.add(tempDir.resolve("longer/path/to/my/very/best/thirdSong.wav"));
            tempPlaylistFile = tempPlaylistDir.resolve("tempPlaylistFile.amp42").toFile();

            FileWriter writer = null;
            try {
                writer = new FileWriter(tempPlaylistFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Path path: playlistPaths) {
                try {
                    writer.write(path.toAbsolutePath().toString());
                    writer.write("\n");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            // prepare mocks
            jList = new JList();
            mockedLogger = Mockito.mock(Logger.class);
            mockedChooser = Mockito.mock(JFileChooser.class);

            // prepare class for testing its method
            executor = new FileSelectorGUIExecutorV1(mockedLogger);
            executor.initialize(mockedChooser, jList);
            executor.setupPlaylistWindow();
        }

        @Test
        void testLoadPlaylistCorrectNumberOfItems() {
            executor.loadPlaylist(tempPlaylistFile.getAbsolutePath());
            assertEquals(playlistPaths.size(), executor.getJPlaylist().getPlaylist().size());
        }

        @Test
        void testLoadPlaylistCorrectNamesAndPaths() {
            executor.loadPlaylist(tempPlaylistFile.getAbsolutePath());
            for (int i = 0; i< playlistPaths.size(); i++) {
                assertEquals(playlistPaths.get(i).toFile().getAbsolutePath(),
                        executor.getJPlaylist().getPlaylist().get(i).getAudioFile().getAbsolutePath());
            }
        }

        @Test
        void testLoadPlaylistFirstSongInAudioBackEnd() {
            String playlistPath = System.getProperty("user.dir") + "/src/test/resources/playlist.amp42";
            executor.loadPlaylist(playlistPath);
            assertEquals(Globals.backend.getFile().getAbsolutePath(),
                    executor.getJPlaylist().getPlaylist().get(0).getAudioFile().getAbsolutePath());
        }

        @Test
        void testLoadFile() {
            String filePath = System.getProperty("user.dir") + "/src/test/resources/piano_loops.wav";
            executor.loadFile(filePath);
            assertEquals(filePath, Globals.backend.getFile().getAbsolutePath());
        }
    }

    @Test
    void testSavePlaylist() {
        FileSelectorGUIExecutorV1 executor = new FileSelectorGUIExecutorV1(Mockito.mock(Logger.class));
        executor.initialize(Mockito.mock(JFileChooser.class), Mockito.mock(JList.class));
        executor.setupFileChooser();
        SaveScreenGUI gui = executor.savePlaylist();
        assertEquals(executor.getJPlaylist(), gui.getJPlaylist());
    }
}