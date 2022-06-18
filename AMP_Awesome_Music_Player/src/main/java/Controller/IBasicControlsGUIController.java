package Controller;

public interface IBasicControlsGUIController {

    enum ControllerState {PLAYING, RESTING};
    void startAction();
    void stopAction();
    void pauseAction();

    ControllerState getcontrollerState();
}
