package GUI;

import Audio.AudioBackEnd;
import FileUtils.JPlaylist;
import Logging.Logger;

import javax.swing.*;

public class Globals {

    // constants
    public static final JPlaylist currentPlaylist = new JPlaylist(new JList(new DefaultListModel<String>()));
    public static final Logger logger = new Logger();
    public static final AudioBackEnd backend = new AudioBackEnd(logger);

    // global state variables
    public static boolean isShuffleEnabled = true;
}
