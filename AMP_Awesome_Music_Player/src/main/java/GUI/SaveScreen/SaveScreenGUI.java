package GUI.SaveScreen;

import Adapter.SaveScreen.SaveScreenGUIAdapter;
import Controller.SaveScreen.ISaveScreenGUIController;
import FileUtils.AvailableFileTypes;
import FileUtils.JPlaylist;
import lombok.Data;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

@Data
public class SaveScreenGUI {

    JFileChooser jFileChooser;
    JPlaylist jPlaylist;

    JFrame frame;

    JButton saveButton;
    JButton quitButton;

    JTextField textField;

    JPanel panel;

    SaveScreenGUIAdapter adapter;
    public SaveScreenGUI(JPlaylist jPlaylist, ISaveScreenGUIController controller) {
        jFileChooser = new JFileChooser();
        frame = new JFrame();
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");
        textField = new JTextField("Filename (without .amp42 ending)");
        panel = new JPanel();
        this.jPlaylist = jPlaylist;

        adapter = new SaveScreenGUIAdapter(this, controller);
    }

    public void execute() {
        // add elements to frame
        panel.add(jFileChooser);
        panel.add(saveButton);
        panel.add(quitButton);
        panel.add(textField);

        // adjust frame size to fit content and display it
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // todo set file filter to only amp42, set button names to "Save" and "Quit"
        jFileChooser.setControlButtonsAreShown(false);
        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(AvailableFileTypes.PLAYLIST_FILE, "playlist file"));

        // set visible
        frame.setVisible(true);

    }
}
