package Main;

import Audio.AudioBackEnd;
import Executor.BasicControlsGUIExecutorV1;
import Executor.FileSelectorGUIExecutorV1;
import GUI.*;
import Logging.Logger;

import javax.swing.*;

public class AMPV1 {
    final AMPGUI ampgui;
    final AnimationGUI animationGUI;
    final BasicControlsGUI basicControlsGUI;
    final FileSelectorGUI fileSelectorGUI;
    final ParametersGUI parametersGUI;
    final TrackPositionGUI trackPositionGUI;

    final AudioBackEnd audioBackEnd;
    final Logger logger;
    final JList list;

    public AMPV1() {
        logger = new Logger();
        audioBackEnd = new AudioBackEnd(logger);
        list = new JList();
        animationGUI = new AnimationGUI();
        basicControlsGUI = new BasicControlsGUI(new BasicControlsGUIExecutorV1(audioBackEnd, logger));
        fileSelectorGUI = new FileSelectorGUI(new FileSelectorGUIExecutorV1(logger));
        parametersGUI = new ParametersGUI();
        trackPositionGUI = new TrackPositionGUI();
        ampgui = new AMPGUI(animationGUI, basicControlsGUI, fileSelectorGUI, parametersGUI, trackPositionGUI);
    }
}
