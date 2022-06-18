package Controller;

import Controller.SaveScreen.ISaveScreenGUIController;
import FileUtils.JPlaylist;
import FileUtils.Playlist;
import GUI.SaveScreen.SaveScreenGUI;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public interface IFileSelectorGUIController {

    void initialize(JFileChooser chooser, JList list);
    File loadFile(String filePath);
    JPlaylist loadPlaylist(String playlistFile);
    SaveScreenGUI savePlaylist();

    void setupFileChooser();

    void setupPlaylistWindow();

    void shuffleList();

    ArrayList<Integer> removeElements();

    ArrayList<Integer> getSelectedListElements();

    void setSelectedListElements(ArrayList<Integer> selectedIndices);

    <T> void guiListInteractions(T interaction);
}
