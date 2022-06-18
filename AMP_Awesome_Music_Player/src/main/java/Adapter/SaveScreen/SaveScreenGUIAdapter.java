package Adapter.SaveScreen;

import Controller.SaveScreen.ISaveScreenGUIController;
import GUI.SaveScreen.SaveScreenGUI;

import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveScreenGUIAdapter implements ActionListener {

    SaveScreenGUI gui;
    ISaveScreenGUIController controller;

    public SaveScreenGUIAdapter(SaveScreenGUI gui, ISaveScreenGUIController controller) {
        this.gui = gui;
        this.controller = controller;

        this.gui.getJFileChooser().addActionListener(this);
        this.gui.getSaveButton().addActionListener(this);
        this.gui.getQuitButton().addActionListener(this);
        //this.gui.getTextField().addActionListener(this);

        this.controller.setGUI(gui);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == gui.getSaveButton()) {
            controller.saveFile();
        }

        if (actionEvent.getSource() == gui.getQuitButton()) {
            controller.quitSaving();
        }
    }
}
