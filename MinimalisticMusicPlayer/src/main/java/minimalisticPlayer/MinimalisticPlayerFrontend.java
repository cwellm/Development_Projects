package minimalisticPlayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinimalisticPlayerFrontend {
    protected final FileHandler fileHandler;
    protected MusicButtonControls buttonControls;
    protected JButton buttonPlay, buttonStop, buttonPause, buttonOpenFile;
    protected MusicButtonControlsBuilder builder;
    protected JSlider slider;
    private final JFrame frame;
    private final JPanel panel;
    private final GUIActionListener listener;

    protected MinimalisticPlayerFrontend() {

        //Create and set up the window frame.
        frame = new JFrame("MinimalPlayerGUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add a panel
        panel = new JPanel();

        // add buttons with icons
        Icon iconPlay = new ImageIcon("resources/button_PLAY.png");
        buttonPlay = new JButton(iconPlay);
        Icon iconStop = new ImageIcon("resources/button_STOP.png");
        buttonStop = new JButton(iconStop);
        Icon iconPause = new ImageIcon("resources/button_PAUSE.png");
        buttonPause = new JButton(iconPause);
        Icon iconOpenFile = new ImageIcon("resources/icon_OPENFILE.png");
        buttonOpenFile = new JButton(iconOpenFile);
        panel.add(buttonPlay);
        panel.add(buttonStop);
        panel.add(buttonPause);
        panel.add(buttonOpenFile);

        // add slider - initial values, will be adjusted to musical piece being played
        slider = new JSlider(0,1,0);
        panel.add(slider);

        // add listeners for actions to the buttons
        fileHandler = new FileHandler(frame);
        builder = new MusicButtonControlsBuilder(fileHandler,this);
        buttonControls = builder.build();
        listener = new GUIActionListener(this);
        buttonPlay.addActionListener(listener);
        buttonStop.addActionListener(listener);
        buttonPause.addActionListener(listener);
        buttonOpenFile.addActionListener(listener);
        slider.addMouseListener(listener);

    }

    /**
     * Start the Swing thread with all necessary configuration
     */
    public void execute() {
        // add panel to frame
        frame.add(panel);

        // adjust frame size to fit content and display it
        frame.pack();
        frame.setVisible(true);
    }
}
