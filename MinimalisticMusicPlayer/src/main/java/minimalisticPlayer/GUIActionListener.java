package minimalisticPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIActionListener implements ActionListener {

    protected JButton buttonPlay, buttonStop, buttonPause, buttonOpenFile;
    private final FileHandler fileHandler;
    private final MusicButtonControlsBuilder builder;
    private final MinimalisticPlayerFrontend frontend;
    private MusicButtonControls buttonControls;

    GUIActionListener(MinimalisticPlayerFrontend frontend) {
        this.frontend = frontend;
        this.buttonPlay = frontend.buttonPlay;
        this.buttonStop = frontend.buttonStop;
        this.buttonPause = frontend.buttonPause;
        this.buttonOpenFile = frontend.buttonOpenFile;
        this.buttonControls = frontend.buttonControls;
        this.builder = frontend.builder;
        this.fileHandler = frontend.fileHandler;

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == buttonPlay) {
            System.out.println("Play!");
            buttonControls.start();
        }

        if (actionEvent.getSource() == buttonStop) {
            System.out.println("Stop!");
            buttonControls.stop();

        }

        if (actionEvent.getSource() == buttonPause) {
            System.out.println("Pausing music.");
            buttonControls.pause();

        }

        if (actionEvent.getSource() == buttonOpenFile) {
            fileHandler.chooseFile();
            frontend.buttonControls = builder.build();
            updateControls();
        }
    }

    private void updateControls() {
        this.buttonControls = frontend.buttonControls;
    }
}
