package Adapter;

import Controller.IFileSelectorGUIController;
import GUI.FileSelectorGUI;
import lombok.NonNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// todo add mouse listener for double click onto some file in JFileChooser
// todo: overthink design regarding saving and loading playlists (where to input file?...
// ...as extra sceen for file name???
public class FileSelectorGUIAdapter implements ActionListener {

    final FileSelectorGUI gui;
    IFileSelectorGUIController controller;

    public FileSelectorGUIAdapter(FileSelectorGUI gui, IFileSelectorGUIController controller) {
        this.gui = gui;
        this.controller = controller;

        this.gui.savePlaylist.addActionListener(this);
        this.gui.loadPlaylist.addActionListener(this);
        this.gui.shuffleList.addActionListener(this);
    }

    public void setupFileChooserAndPlaylist(@NonNull JFileChooser chooser, @NonNull JList list) {
        controller.initialize(chooser, list);
        controller.setupFileChooser();
        controller.setupPlaylistWindow();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*
        if (actionEvent.getSource() == gui.savePlaylist) {
            controller.savePlaylist(SOMEFILE...);
        } else if (actionEvent.getSource() == gui.loadPlaylist) {
            controller.loadPlaylist(SOMEFILE...);
        }
         */
        if (actionEvent.getSource() == gui.shuffleList) {
            controller.shuffleList();
        }
    }
}
