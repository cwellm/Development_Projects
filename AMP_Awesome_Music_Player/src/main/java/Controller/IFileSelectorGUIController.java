package Controller;

import java.io.File;
import java.util.ArrayList;

public interface IFileSelectorGUIController {
    File loadFile();
    ArrayList<File> loadPlaylist(String playlistFile);
    void savePlaylist(String playlistFile);

}
