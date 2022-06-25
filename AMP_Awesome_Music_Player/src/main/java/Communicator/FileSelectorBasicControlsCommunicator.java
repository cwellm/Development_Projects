package Communicator;

import Controller.IBasicControlsDispatcher;
import Controller.IBasicControlsGUIController;
import Controller.IFileSelectorDispatcher;

public class FileSelectorBasicControlsCommunicator {

    private IBasicControlsDispatcher basicControlsDispatcher;
    private IFileSelectorDispatcher fileSelectorDispatcher;

    public FileSelectorBasicControlsCommunicator() {
    }

    public void setBasicControlsDispatcher(IBasicControlsDispatcher basicControlsDispatcher) {
        this.basicControlsDispatcher = basicControlsDispatcher;
    }

    public void setFileSelectorDispatcher(IFileSelectorDispatcher fileSelectorDispatcher) {
        this.fileSelectorDispatcher = fileSelectorDispatcher;
    }

    public void dispatchBasicControlsSignalToFileSelector(IBasicControlsGUIController.ControllerState controllerState) {
        if (controllerState == IBasicControlsGUIController.ControllerState.PLAYING) {
            fileSelectorDispatcher.setToNextListPosition();
        } else if (controllerState == IBasicControlsGUIController.ControllerState.RESTING) {
            fileSelectorDispatcher.setToListPosition(0);
        }
    }

    public void dispatchPlay() {
        basicControlsDispatcher.triggerSongPlay();
    }

    public void dispatchStop() {
        basicControlsDispatcher.triggerSongStop();
    }
}
