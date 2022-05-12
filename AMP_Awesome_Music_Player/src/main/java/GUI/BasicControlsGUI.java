package GUI;

import Adapter.BasicControlsGUIAdapter;
import Controller.IBasicControlsGUIController;

import javax.swing.JButton;

public class BasicControlsGUI {
    public final JButton playButton;
    public final JButton stopButton;
    public final JButton pauseButton;
    final BasicControlsGUIAdapter adapter;
    // This controller is meant to be implemented by the respective executors coordinated by AMPVX, where X stands for an AMP version
    final IBasicControlsGUIController controller;

    public BasicControlsGUI(IBasicControlsGUIController controller) {
        this.controller = controller;
        adapter = new BasicControlsGUIAdapter(this, controller);

        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        pauseButton = new JButton("Pause");
    }

}
