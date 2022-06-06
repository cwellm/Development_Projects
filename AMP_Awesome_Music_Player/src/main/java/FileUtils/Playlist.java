package FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;

public class Playlist {
    private int playingPosition;
    private File songFilePath;
    private String songFileName;

    // Integer: playlist position; File: audio file; String: song name (derived from filename)
    ArrayList<Entries> playlist;

    public Playlist(File playlistFile) {
        readPlaylistFromFile(playlistFile);
    }

    public Playlist() {
        playlist = new ArrayList<>();
    }

    // todo: check whether a list of Java records might be better for the playlist
    public ArrayList<Entries> getPlaylist() {
        return playlist;
    }
    public void setPlaylist(ArrayList<Entries> playlist) {
        this.playlist = playlist;
    }

    public void addToPlaylist(int position, File file) {
        String filePath = file.getAbsolutePath();
        String fileName = NameConverter.toSongName(filePath);

        if (getPlaylist().isEmpty()) {
            playlist.add(0, new Entries(file));
        } else if (position == playlist.size()) {
            playlist.add(new Entries(file));
        } else {
            playlist.add(position, new Entries(file));
        }
    }

    public void reorderPlaylist(int startPosition, int endPosition, File file) {

    }

    public void shufflePlaylist() {
        if (playlist.size() < 2) {
            return;
        }

        int randomPosition = 0;
        ArrayList<Entries> result = new ArrayList<>();
        while (playlist.size() >= 2) {
            randomPosition = (int) (Math.random()*playlist.size());
            result.add(playlist.get(randomPosition));
            playlist.remove(randomPosition);
        }
        result.add(playlist.get(0));
        playlist.clear();
        result.forEach(r -> {playlist.add(r);});
    }

    private void readPlaylistFromFile(File playlistFile) {
        playlist = new ArrayList<>();

        try {
            ArrayList<String> audioFilePaths = (ArrayList<String>) Files.readAllLines(Path.of(playlistFile.getAbsolutePath()));
            for (String audioFilePath: audioFilePaths) {
                playlist.add(new Entries(new File(audioFilePath)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            // todo actually, logger is better here - overthink current concept of logger, and where to place it!
            // Possibly, there is already a convenient Java class for that?
            System.out.println("File could not be read.");
        }
    }
}
