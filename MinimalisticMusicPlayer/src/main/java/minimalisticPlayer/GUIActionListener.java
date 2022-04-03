package minimalisticPlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUIActionListener implements ActionListener, MouseListener {

    protected JButton buttonPlay, buttonStop, buttonPause, buttonOpenFile;
    protected JSlider slider;
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
        this.slider = frontend.slider;
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
            updateSlider();
        }
    }

    private void updateControls() {
        this.buttonControls = frontend.buttonControls;
    }

    private void updateSlider() {
        long fileSize = fileHandler.getMusicFile().length();
        slider.setMaximum((int)fileSize);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        JSlider source = (JSlider)mouseEvent.getSource();
        if (source.equals(slider)) {
            long frameCompatibleBytePosition = ((long)source.getValue()) -
                    ((long)source.getValue()) % frontend.buttonControls.getMyStream().getFormat().getFrameSize();
            System.out.println(frameCompatibleBytePosition);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
