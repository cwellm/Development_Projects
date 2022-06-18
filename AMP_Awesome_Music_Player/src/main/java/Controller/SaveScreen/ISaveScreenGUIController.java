package Controller.SaveScreen;

import GUI.SaveScreen.SaveScreenGUI;

public interface ISaveScreenGUIController {

    boolean saveFile();

    void quitSaving();

    void setGUI(SaveScreenGUI gui);
}
