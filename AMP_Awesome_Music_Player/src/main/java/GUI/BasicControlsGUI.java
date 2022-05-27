package GUI;

import Adapter.BasicControlsGUIAdapter;
import Controller.IBasicControlsGUIController;

import javax.swing.*;
import java.awt.*;

public class BasicControlsGUI {
    public final JButton playButton;
    public final JButton stopButton;
    public final JButton pauseButton;
    final BasicControlsGUIAdapter adapter;
    // This controller is meant to be implemented by the respective executors coordinated by AMPVX, where X stands for an AMP version
    final IBasicControlsGUIController controller;

    final JPanel panel;

    public BasicControlsGUI(IBasicControlsGUIController controller) {
        this.controller = controller;

        Icon playIcon = new ImageIcon("src/main/resources/button_PLAY.png");
        playButton = new JButton(playIcon);

        Icon stopIcon = new ImageIcon("src/main/resources/button_STOP.png");
        stopButton = new JButton(stopIcon);

        Icon pauseIcon = new ImageIcon("src/main/resources/button_PAUSE.png");
        pauseButton = new JButton(pauseIcon);

        adapter = new BasicControlsGUIAdapter(this, controller);

        panel = new JPanel();
        panel.add(playButton);
        panel.add(stopButton);
        panel.add(pauseButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
