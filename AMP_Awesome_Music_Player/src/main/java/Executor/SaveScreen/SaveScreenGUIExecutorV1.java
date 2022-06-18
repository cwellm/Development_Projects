package Executor.SaveScreen;

import Controller.SaveScreen.ISaveScreenGUIController;
import FileUtils.JPlaylist;
import GUI.SaveScreen.SaveScreenGUI;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class SaveScreenGUIExecutorV1 implements ISaveScreenGUIController {

    final JPlaylist jPlaylist;
    SaveScreenGUI gui;
    public SaveScreenGUIExecutorV1(JPlaylist jPlaylist) {
        this.jPlaylist = jPlaylist;
    }

    @Override
    public boolean saveFile() {
        File currentDirectory = gui.getJFileChooser().getCurrentDirectory();
        String saveFileName = gui.getTextField().getText();

        File output = Path.of(currentDirectory.toString(), saveFileName + ".amp42").toFile();
        try {
            output.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(output, false));
            for (int i=0; i<jPlaylist.getPlaylist().size(); i++) {
                writer.write(jPlaylist.getPlaylist().get(i).getAudioFile().getAbsolutePath());
                if (i != jPlaylist.getPlaylist().size() - 1) {
                    writer.write("\n");
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void quitSaving() {
        gui.getFrame().dispose();
    }

    public void setGUI(SaveScreenGUI gui) {
        this.gui = gui;
    }
}
