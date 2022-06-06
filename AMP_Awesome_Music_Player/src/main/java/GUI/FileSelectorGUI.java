package GUI;

import Adapter.FileSelectorGUIAdapter;
import Controller.IFileSelectorGUIController;

import javax.swing.*;

// todo: add shuffle button/option
public class FileSelectorGUI {

    public JButton loadPlaylist;

    public JButton savePlaylist;

    public JButton shuffleList;

    final FileSelectorGUIAdapter adapter;
    final IFileSelectorGUIController controller;
    private JPanel panel;
    private JFileChooser chooser;
    private JFileChooser saveLoadChooser;
    private JList<String> jlist;



    public FileSelectorGUI(IFileSelectorGUIController controller) {
        this.controller = controller;

        chooser = new JFileChooser();
        jlist = new JList<>();
        savePlaylist = new JButton("Save Playlist");
        loadPlaylist = new JButton("Load Playlist");
        shuffleList = new JButton("Shuffle!");

        adapter = new FileSelectorGUIAdapter(this, controller);
        adapter.setupFileChooserAndPlaylist(chooser, jlist);

        panel = new JPanel();
        panel.add(chooser);
        panel.add(jlist);
        panel.add(savePlaylist);
        panel.add(loadPlaylist);
        panel.add(shuffleList);
    }

    public JPanel getPanel() {
        return panel;
    }
}
