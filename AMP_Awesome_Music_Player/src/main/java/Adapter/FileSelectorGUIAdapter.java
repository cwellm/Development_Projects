package Adapter;

import Communicator.FileSelectorBasicControlsCommunicator;
import Controller.IFileSelectorGUIController;
import GUI.FileSelectorGUI;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// todo add mouse listener for double click onto some file in JFileChooser
// todo: overthink design regarding saving and loading playlists (where to input file?...
// ...as extra sceen for file name???
public class FileSelectorGUIAdapter implements ActionListener, ListSelectionListener, KeyListener {

    final FileSelectorGUI gui;

    IFileSelectorGUIController controller;

    public FileSelectorGUIAdapter(FileSelectorGUI gui, IFileSelectorGUIController controller) {
        this.gui = gui;
        this.controller = controller;

        this.gui.savePlaylist.addActionListener(this);
        this.gui.shuffleList.addActionListener(this);

        this.gui.jlist.addKeyListener(this);

    }

    public void setupFileChooserAndPlaylist() {
        ListSelectionModel lsm = gui.jlist.getSelectionModel();
        lsm.addListSelectionListener(this);
        controller.initialize(gui.chooser, gui.jlist);
        controller.setupFileChooser();
        controller.setupPlaylistWindow();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == gui.shuffleList) {
            controller.shuffleList();
        } else if (actionEvent.getSource() == gui.savePlaylist) {
            controller.savePlaylist();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        controller.guiListInteractions(lsm);
    }

    // todo: actually, key bindings are better in Swing than key listeners
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        controller.guiListInteractions(keyEvent);
    }
}
