package minimalisticPlayer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Utility class for the handling of the files for the {@link MinimalisticPlayerFrontend} GUI;
 * Includes also a JFileChooser to be called when file shall be selected from the GUI
 */
public class FileHandler extends JPanel {
    private static final String[] SUPPORTED_FORMATS = {"wav"};
    private final JFileChooser chooser;
    private File musicFile;
    private final Component fileChooserParent;
    private final FileFilter filter;

    public FileHandler() {
        chooser = new JFileChooser();
        fileChooserParent = null;
        filter = new FileNameExtensionFilter("Supported Audio File Formats", SUPPORTED_FORMATS);
        chooser.addChoosableFileFilter(filter);

    }

    public FileHandler(Component fileChooserParent) {
        chooser = new JFileChooser();
        this.fileChooserParent = fileChooserParent;
        filter = new FileNameExtensionFilter("Supported Audio File Formats", SUPPORTED_FORMATS);
        chooser.addChoosableFileFilter(filter);

    }

    public File chooseFile() {
        int checkChooserVal = chooser.showOpenDialog(fileChooserParent);
        if (checkChooserVal == JFileChooser.APPROVE_OPTION) {
            return musicFile = chooser.getSelectedFile();
        }
        System.out.println("No supported format!");
        return null;
    }

    public File getMusicFile() {
        return this.musicFile;
    }
}