package Executor;

import Controller.IFileSelectorDispatcher;
import Controller.IFileSelectorGUIController;
import Controller.SaveScreen.ISaveScreenGUIController;
import Executor.SaveScreen.SaveScreenGUIExecutorV1;
import FileUtils.AvailableFileTypes;
import FileUtils.JPlaylist;
import FileUtils.Playlist;
import GUI.Globals;
import GUI.SaveScreen.SaveScreenGUI;
import Logging.Logger;
import FileUtils.NameConverter;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// todo: focus the currently selected song in the list in the GUI

public class FileSelectorGUIExecutorV1 implements IFileSelectorGUIController, IFileSelectorDispatcher {

    private Playlist playlist;
    private JPlaylist jPlaylist;

    private JList list;
    private JFileChooser chooser;

    private ArrayList<Integer> selectedIndices;

    // todo overthink this design...the constructor is now in "initialize" and called from the outside...
    // ...maybe create an abstract class implementing the interface, which inherits to this class then
    public FileSelectorGUIExecutorV1(Logger logger) {
        selectedIndices = new ArrayList<>();
    }

    @Override
    public void initialize(@NonNull JFileChooser chooser, @NonNull JList list) {
        this.chooser = chooser;
        this.list = list;
    }

    @Override
    public File loadFile(String filePath) {
        File file = new File(filePath);
        Globals.backend.setFile(file);
        Globals.backend.setIfFileFormatSupported();
        Globals.backend.setIfAudioInputLineAvailable();
        return file;
    }

    @Override
    public JPlaylist loadPlaylist(String playlistFile) {
        jPlaylist.clear();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(playlistFile))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                jPlaylist.addToPlaylist(new File(line));
            }
        } catch (IOException x) {
            // todo suitable behavior
        }

        Globals.backend.setFile(jPlaylist.getPlaylist().get(0).getAudioFile());
        System.out.println(Globals.backend.getFile().getAbsolutePath());
        Globals.backend.setIfFileFormatSupported();
        Globals.backend.setIfAudioInputLineAvailable();

        return jPlaylist;
    }

    @Override
    public SaveScreenGUI savePlaylist() {
        SaveScreenGUIExecutorV1 executor = new SaveScreenGUIExecutorV1(jPlaylist);
        SaveScreenGUI saveGui =  new SaveScreenGUI(jPlaylist, executor);
        saveGui.execute();
        return saveGui;
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
        jPlaylist = new JPlaylist(list);
        jPlaylist.addToPlaylist(0, new File("test/path.wav"));
    }

    @Override
    public void shuffleList() {
        jPlaylist.shufflePlaylist();
    }

    @Override
    public ArrayList<Integer> removeElements() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Integer> getSelectedListElements() {
        return null;
    }

    @Override
    public void setSelectedListElements(ArrayList<Integer> selectedIndices) {
        this.selectedIndices.clear();
        this.selectedIndices.addAll(selectedIndices);
    }

    @Override
    public <T> void guiListInteractions(T interaction) {

        // instance of a ListSelectionModel
        if (interaction instanceof ListSelectionModel) {
            ListSelectionModel model = (ListSelectionModel) interaction;

            ArrayList<Integer> selectedIndices =
                    (ArrayList) Arrays.stream(model.getSelectedIndices()).boxed().collect(Collectors.toList());
            setSelectedListElements(selectedIndices);
        }

        // instance of a KeyEvent
        else if (interaction instanceof KeyEvent) {
            KeyEvent event = (KeyEvent) interaction;

            // the Jlist where the playlist files are displayed
            if (event.getSource().equals(list)) {
                ListSelectionModel model = ((JList) event.getSource()).getSelectionModel();

                switch(event.getKeyCode()) {
                    case KeyEvent.VK_DELETE:
                        jPlaylist.removeFromPlaylist(selectedIndices);
                        break;

                    case KeyEvent.VK_ESCAPE:
                        model.clearSelection();
                        selectedIndices.clear();
                        break;

                    default:
                }
            }
        }
    }

    @Override
    public void setToNextListPosition() {

    }

    @Override
    public void setToListPosition(int listPosition) {

    }

    @Override
    public int getCurrentPlayingIndex() {
        return 1;
    }

    @Override
    public ArrayList<Integer> getSelectedIndices() {
        return selectedIndices;
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
                return false;
            }

            if (filePath.endsWith(".amp42")) {
                loadPlaylist(filePath);
                return true;
            }

            // get drop location
            JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
            int dropIndex = dl.getIndex();
            jPlaylist.addToPlaylist(dropIndex, new File(filePath));

            // load the imported data into the audio player
            loadFile(filePath);
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

    public JPlaylist getJPlaylist() {
        return jPlaylist;
    }
}
