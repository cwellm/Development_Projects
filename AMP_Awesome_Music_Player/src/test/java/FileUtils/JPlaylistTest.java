package FileUtils;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JPlaylistTest {

    static File testfile;
    static JList list;

    @BeforeAll
    public static void setup() {
        testfile = new File("src/test/resources/playlist.txt");
        list = new JList<>(new DefaultListModel<String>());
    }

    @Test
    public void testFileConstructor() {
        JList list = new JList<>(new DefaultListModel<String>());
        JPlaylist jPlaylist = new JPlaylist(list, testfile);
        assertEquals(jPlaylist.getPlaylist().size(), jPlaylist.getJList().length);
    }

    @Test
    public void testSongList() {
        JPlaylist jPlaylist = new JPlaylist(list, testfile);
        for (int playlistPosition = 0; playlistPosition < jPlaylist.getPlaylist().size(); playlistPosition ++ ) {
            assertTrue(jPlaylist.getSongNames().contains(jPlaylist.getPlaylist().get(playlistPosition).getSongName()));
        }
    }

    @Test
    public void testShufflePlaylist() {
        JPlaylist jPlaylist = new JPlaylist(list, testfile);
        jPlaylist.shufflePlaylist();
        for (int playlistPosition = 0; playlistPosition < jPlaylist.getPlaylist().size(); playlistPosition ++ ) {
            assertTrue(jPlaylist.getSongNames().contains(jPlaylist.getPlaylist().get(playlistPosition).getSongName()));
        }
        assertEquals(jPlaylist.getPlaylist().size(), jPlaylist.getJList().length);
    }
}