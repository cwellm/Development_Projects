package FileUtils;

import lombok.NonNull;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class JPlaylist extends Playlist {
    private DefaultListModel listModel;
    private ArrayList<String> songNames;

    public JPlaylist(@NonNull JList list) {
        super();
        listModel = (DefaultListModel) list.getModel();
        songNames = new ArrayList<>();
    }

    public JPlaylist(@NonNull JList list, File playlistFile) {
        super(playlistFile);
        listModel = (DefaultListModel) list.getModel();
        songNames = (ArrayList<String>) playlist.stream()
                .map(Entries::getAudioFile)
                .map(file -> {return NameConverter.toSongName(file);})
                .collect(Collectors.toList());

        songNames.forEach(song -> {listModel.addElement(song);});
    }

    @Override
    public void addToPlaylist(int position, File file) {
        super.addToPlaylist(position, file);
        if (listModel.isEmpty()) {
            listModel.addElement(NameConverter.toSongName(file));
        } else {
            listModel.add(position, NameConverter.toSongName(file));
        }
    }

    @Override
    public void shufflePlaylist() {
        super.shufflePlaylist();

        if (listModel.getSize() >=2) {
            listModel.clear();
            songNames = (ArrayList<String>) playlist.stream()
                    .map(Entries::getSongName)
                    .collect(toList());

            listModel.addAll(songNames);
        }
    }

    public Object[] getJList() {
        return listModel.toArray();
    }

    public ArrayList<String> getSongNames() {
        return songNames;
    }
}
