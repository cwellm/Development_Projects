package GUI;

import javax.swing.*;

public class AMPGUI {
    private final AnimationGUI animationGUI;
    private final BasicControlsGUI basicControlsGUI;
    private final FileSelectorGUI fileSelectorGUI;
    private final ParametersGUI parametersGUI;
    private final TrackPositionGUI trackPositionGUI;

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
    }
}
