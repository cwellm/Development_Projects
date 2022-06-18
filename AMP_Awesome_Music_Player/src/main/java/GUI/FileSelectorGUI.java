package GUI;

import Adapter.FileSelectorGUIAdapter;
import Controller.IFileSelectorGUIController;

import javax.swing.*;

// todo: add shuffle button/option
public class FileSelectorGUI {

    public final JButton savePlaylist;

    public final  JButton shuffleList;
    public final JFileChooser chooser;
    public final JList<String> jlist;

    final FileSelectorGUIAdapter adapter;
    final IFileSelectorGUIController controller;
    private JPanel panel;

    public FileSelectorGUI(IFileSelectorGUIController controller) {
        this.controller = controller;

        chooser = new JFileChooser();
        jlist = new JList<>();
        savePlaylist = new JButton("Save Playlist");
        shuffleList = new JButton("Shuffle!");

        adapter = new FileSelectorGUIAdapter(this, controller);
        adapter.setupFileChooserAndPlaylist();

        panel = new JPanel();
        panel.add(chooser);
        panel.add(jlist);
        panel.add(savePlaylist);
        panel.add(shuffleList);
    }

    public JPanel getPanel() {
        return panel;
    }
}
