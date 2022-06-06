package Executor;

import Controller.IFileSelectorGUIController;
import FileUtils.AvailableFileTypes;
import FileUtils.JPlaylist;
import FileUtils.Playlist;
import Logging.Logger;
import FileUtils.NameConverter;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSelectorGUIExecutorV1 implements IFileSelectorGUIController {

    private Playlist playlist;
    private JPlaylist jPlaylist;

    private JList list;
    private JFileChooser chooser;

    // todo overthink this design...the constructor is now in "initialize" and called from the outside...
    public FileSelectorGUIExecutorV1(Logger logger) {
    }

    @Override
    public void initialize(@NonNull JFileChooser chooser, @NonNull JList list) {
        this.chooser = chooser;
        this.list = list;
    }

    @Override
    public File loadFile() {
        //todo implement loadFile
        return null;
    }

    @Override
    public Playlist loadPlaylist(String playlistFile) {
        // todo implement loadPlaylist
        return null;
    }

    @Override
    public void savePlaylist(String playlistFile) {
        // todo implement savePlaylist
    }

    @Override
    public void setupFileChooser() {
        chooser.setDragEnabled(true);
        chooser.setControlButtonsAreShown(false);
        chooser.setAcceptAllFileFilterUsed(true);

        for (String type: AvailableFileTypes.AVAILABLE_FILE_TYPES) {
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(type, type));
        }
    }

    @Override
    public void setupPlaylistWindow() {
        DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.ON_OR_INSERT);
        list.setTransferHandler(new JlistTransferHandler());
        // playlist = new Playlist();
        jPlaylist = new JPlaylist(list);
        jPlaylist.addToPlaylist(0, new File("test/path.wav"));
    }

    @Override
    public void shuffleList() {
        jPlaylist.shufflePlaylist();
    }

    class JlistTransferHandler extends TransferHandler {

        @Override
        public boolean importData(TransferSupport support) {
            String filePath = "";
            String fileName = "";
            try {
                filePath = support.getTransferable().getTransferData(DataFlavor.stringFlavor).toString();
                fileName = NameConverter.toSongName(filePath);
            }
            catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }

            // get drop location
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int dropIndex = dl.getIndex();
            jPlaylist.addToPlaylist(dropIndex, new File(filePath));
            return true;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return false;
            }
            return true;
        }
    }

    // todo: better to update JList according to playlist (tying them together)? And without parameters?
    // todo YES: SEE JPlaylist class - take Playlist and add the JList to it!
    private void updateJlist(JList list, int dropIndex, String fileName) {
        DefaultListModel listModel = (DefaultListModel)list.getModel();
        listModel.add(dropIndex, fileName);
    }
}
