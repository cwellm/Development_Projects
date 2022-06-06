package FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NameConverter {

    public static File nameToFile(String name) {
        return new File(name);
    }

    public static String fileToString(File file) {
        return file.getAbsolutePath().toString();
    }

    public static String toSongName(File file) {
        String pathName = fileToString(file);
        return toSongName(pathName);
    }

    public static String toSongName(String pathName) {
        Path path = Paths.get(pathName);
        String fileName = path.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        return fileName.substring(0, lastDot);

    }
}