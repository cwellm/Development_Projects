package Communicator;

import Controller.IBasicControlsDispatcher;
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


}
