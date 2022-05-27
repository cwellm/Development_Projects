package Main;

import Audio.AudioBackEnd;
import Executor.BasicControlsGUIExecutorV1;
import GUI.*;
import Logging.Logger;

public class AMPV1 {
    final AMPGUI ampgui;
    final AnimationGUI animationGUI;
    final BasicControlsGUI basicControlsGUI;
    final FileSelectorGUI fileSelectorGUI;
    final ParametersGUI parametersGUI;
    final TrackPositionGUI trackPositionGUI;

    final AudioBackEnd audioBackEnd;
    final Logger logger;

    public AMPV1() {
        logger = new Logger();
        audioBackEnd = new AudioBackEnd(logger);
        animationGUI = new AnimationGUI();
        basicControlsGUI = new BasicControlsGUI(new BasicControlsGUIExecutorV1(audioBackEnd, logger));
        fileSelectorGUI = new FileSelectorGUI();
        parametersGUI = new ParametersGUI();
        trackPositionGUI = new TrackPositionGUI();
        ampgui = new AMPGUI(animationGUI, basicControlsGUI, fileSelectorGUI, parametersGUI, trackPositionGUI);
    }
}
