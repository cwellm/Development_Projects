/**
 * Some learning:
 * https://blogs.oracle.com/javamagazine/
 * https://www.oracle.com/technical-resources/articles.html
 */

package Main;

import Audio.AudioBackEnd;
import Executor.BasicControlsGUIExecutorV1;
import Executor.FileSelectorGUIExecutorV1;
import GUI.AMPGUI;
import GUI.BasicControlsGUI;
import GUI.FileSelectorGUI;
import GUI.Globals;
import Logging.Logger;

import javax.swing.*;
import java.io.File;

// todo: add mp3 support (see e.g. mp3agic)
// todo: how to test "isAudioInputLineAvailable"? - Problem: AudioSystem cannot be instantiated
// todo: write test for AudioBackEnd - no need to mock AudioSystem
// todo: How do Gradle and Maven interact? How to set dependencies for Gradle as parameter? What if only Maven artifact is available?...such as mp3agic?


// todo: implement wait() and notify() in the correct way instead of letting the Thread sleep in a while loop...
// --> this includes also IllegalMonitorException and a keen understanding of how Threads work in Java!
// --> e.g.: https://www.baeldung.com/java-wait-notify
// todo: handle annoying noise when resuming from pause!
// todo: write tests
// todo: remove unneccesary imports and variables
// todo: think about how to add functionality to the player for supporting more file types...how to integrate this, or
// make the player aware that now, the audio system supports more files
// todo: try to use only java.nio instead of java.io, where reasonable
// todo: consistency: all lombok getter and setter, instead of explicitly writing them

public class MainApp {
    public static void main(String[] args) {

        BasicControlsGUIExecutorV1 basicExecutor = new BasicControlsGUIExecutorV1(Globals.backend, Globals.logger);
        final BasicControlsGUI basicGui = new BasicControlsGUI(basicExecutor);
        FileSelectorGUIExecutorV1 fileExecutor = new FileSelectorGUIExecutorV1(Globals.logger);
        final FileSelectorGUI selectorGui = new FileSelectorGUI(fileExecutor);

        AMPGUI ampgui = new AMPGUI(null, basicGui, selectorGui, null, null);

        SwingUtilities.invokeLater(
                () -> {ampgui.execute();}
        );

    }
}
