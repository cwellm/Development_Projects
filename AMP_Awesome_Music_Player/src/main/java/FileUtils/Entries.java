package FileUtils;

import lombok.Getter;

import java.io.File;

// todo: check Java version to make this a record
@Getter
public class Entries {
    private File audioFile;
    private String songName;

    public Entries(File audioFile) {
        this.audioFile = audioFile;
        this.songName = NameConverter.toSongName(audioFile);
    }
}
