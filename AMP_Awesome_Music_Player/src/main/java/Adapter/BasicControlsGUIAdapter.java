package Adapter;

import Communicator.FileSelectorBasicControlsCommunicator;
import Controller.IBasicControlsGUIController;
import GUI.BasicControlsGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicControlsGUIAdapter implements ActionListener {

    final BasicControlsGUI gui;

    IBasicControlsGUIController controller;

    public BasicControlsGUIAdapter(BasicControlsGUI gui, IBasicControlsGUIController controller) {
        this.gui = gui;
        this.gui.playButton.addActionListener(this);
        this.gui.stopButton.addActionListener(this);
        this.gui.pauseButton.addActionListener(this);
        this.controller = controller;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == gui.playButton) {
            controller.startAction();
        } else if (actionEvent.getSource() == gui.stopButton) {
            controller.stopAction();
        } else if (actionEvent.getSource() == gui.pauseButton) {
            controller.pauseAction();
        }
    }
}
