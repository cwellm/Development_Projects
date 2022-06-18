package FileUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class NameConverterTest {

    @Test
    void testNameToFile() {
        String input = "testString";
        assertEquals(new File(input), NameConverter.nameToFile(input));
    }

    @Test
    void testFileToString(@TempDir Path tempDir) {
        Path tempFile = tempDir.resolve("input.wav");
        assertEquals(tempFile.toAbsolutePath().toString(), NameConverter.fileToString(tempFile.toFile()));
    }

    @Test
    void testToSongNameWithStringInput(@TempDir Path tempDir) {
        Path tempFile = tempDir.resolve("path/to/my/input.file");
        assertEquals("input", NameConverter.toSongName(tempFile.toAbsolutePath().toString()));
    }
}