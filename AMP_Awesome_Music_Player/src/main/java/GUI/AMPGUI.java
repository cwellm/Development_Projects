package GUI;

import javax.swing.*;
import java.awt.*;

public class AMPGUI {
    private final AnimationGUI animationGUI;
    private final BasicControlsGUI basicControlsGUI;
    private final FileSelectorGUI fileSelectorGUI;
    private final ParametersGUI parametersGUI;
    private final TrackPositionGUI trackPositionGUI;

    private final JPanel panel;

    private final JFrame frame;

    public AMPGUI(AnimationGUI animationGUI, BasicControlsGUI basicControlsGUI, FileSelectorGUI fileSelectorGUI,
                  ParametersGUI parametersGUI, TrackPositionGUI trackPositionGUI) {
        this.animationGUI = animationGUI;
        this.basicControlsGUI = basicControlsGUI;
        this.fileSelectorGUI = fileSelectorGUI;
        this.parametersGUI = parametersGUI;
        this.trackPositionGUI = trackPositionGUI;

        frame = new JFrame("AMP - Awesome Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.add(basicControlsGUI.getPanel());
        panel.add(fileSelectorGUI.getPanel());
    }

    /**
     * Start the Swing thread with all necessary configuration
     */
    public void execute() {
        // add panel to frame
        frame.add(panel);

        // adjust frame size to fit content and display it
        frame.pack();
        frame.setVisible(true);
    }
}
