package FileUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class PlaylistTest {

    @TempDir
    static Path tempDir;
    static File testfile1, testfile2;

    @BeforeAll
    public static void setup() {
        testfile1 = tempDir.resolve("testfile1.txt").toFile();
        testfile2 = tempDir.resolve("testfile2/audio.wav").toFile();
    }

    @Test
    public void testDefaultConstructor() {
        Playlist playlist = new Playlist();
        assertTrue(playlist.getPlaylist().isEmpty());
    }

    @Test
    public void testFileConstructor() throws FileNotFoundException {
        File file = new File("src/test/resources/playlist.txt");
        ArrayList<String> lines;
        Playlist playlist = new Playlist(file);

        assertEquals(3, playlist.getPlaylist().size());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            lines = (ArrayList<String>) reader.lines().collect(Collectors.toList());

            for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
                assertEquals(lines.get(lineNumber), playlist.getPlaylist().get(lineNumber).getAudioFile().getAbsolutePath());
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("This file does not exist.");
        }
    }

    @Test
    public void testAddToPlaylist() {
        Playlist playlist = new Playlist();
        playlist.addToPlaylist(0, testfile1);
        playlist.addToPlaylist(1, testfile2);
        assertEquals(2, playlist.getPlaylist().size());

        playlist.addToPlaylist(0, testfile2);
        assertEquals(testfile2, playlist.getPlaylist().get(0).getAudioFile());
    }

    @Test
    public void testShufflePlaylist() {
        File file = new File("src/test/resources/playlist.txt");
        Playlist playlist = new Playlist(file);
        ArrayList<Entries> entries = playlist.getPlaylist();

        int playlistSize = playlist.getPlaylist().size();
        playlist.shufflePlaylist();

        assertEquals(playlistSize, playlist.getPlaylist().size());
        for (Entries entry: entries) {
            assertTrue(playlist.getPlaylist().contains(entry));
        }
    }
}