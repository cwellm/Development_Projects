package Main;

import Audio.AudioBackEnd;
import Communicator.FileSelectorBasicControlsCommunicator;
import Controller.IBasicControlsGUIController;
import Controller.IFileSelectorGUIController;
import Executor.BasicControlsGUIExecutorV1;
import Executor.FileSelectorGUIExecutorV1;
import GUI.*;
import Logging.Logger;

import javax.swing.*;

public class AMPV1 {
    private AMPGUI ampgui;
    private AnimationGUI animationGUI;
    private BasicControlsGUI basicControlsGUI;
    private FileSelectorGUI fileSelectorGUI;
    private ParametersGUI parametersGUI;
    private TrackPositionGUI trackPositionGUI;

    public AMPV1() {
        setup();
    }

    public void setup() {

        // Communicators
        FileSelectorBasicControlsCommunicator fileBasicComm = new FileSelectorBasicControlsCommunicator();

        // Interfaces
        IBasicControlsGUIController basicControlsGUIController =
                new BasicControlsGUIExecutorV1(Globals.backend, Globals.logger, fileBasicComm);

        IFileSelectorGUIController fileSelectorGUIController =
                new FileSelectorGUIExecutorV1(Globals.logger, fileBasicComm);

        // GUIs
        animationGUI = new AnimationGUI();
        basicControlsGUI = new BasicControlsGUI(basicControlsGUIController);
        fileSelectorGUI = new FileSelectorGUI(fileSelectorGUIController);
        parametersGUI = new ParametersGUI();
        trackPositionGUI = new TrackPositionGUI();

        // Assemble AMPGUI
        ampgui = new AMPGUI(animationGUI, basicControlsGUI, fileSelectorGUI, parametersGUI, trackPositionGUI);
    }

    public AMPGUI getAmpgui() {
        return ampgui;
    }
}
