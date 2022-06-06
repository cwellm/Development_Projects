package Controller;

import FileUtils.Playlist;

import javax.swing.*;
import java.io.File;

public interface IFileSelectorGUIController {

    void initialize(JFileChooser chooser, JList list);
    File loadFile();
    Playlist loadPlaylist(String playlistFile);
    void savePlaylist(String playlistFile);

    void setupFileChooser();

    void setupPlaylistWindow();

    void shuffleList();
}
