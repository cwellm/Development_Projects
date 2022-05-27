package Main;

import Audio.AudioBackEnd;
import Executor.BasicControlsGUIExecutorV1;
import GUI.AMPGUI;
import GUI.BasicControlsGUI;
import GUI.FileSelectorGUI;
import Logging.Logger;

import javax.swing.*;
import java.io.File;

// todo: add mp3 support (see e.g. mp3agic)
// todo: how to test "isAudioInputLineAvailable"? - Problem: AudioSystem cannot be instantiated
// todo: tests not working properly with Gradle! Right now, test set to "IntelliJ" in settings
// todo: write test for AudioBackEnd - no need to mock AudioSystem
// todo: How do Gradle and Maven interact? How to set dependencies for Gradle as parameter? What if only Maven artifact is available?...such as mp3agic?
// todo: implement wait() and notify() in the correct way instead of letting the Thread sleep in a while loop...
// --> this includes also IllegalMonitorException and a keen understanding of how Threads work in Java!
// --> e.g.: https://www.baeldung.com/java-wait-notify
// todo: handle annoying noise when resuming from pause!
public class MainApp {
    public static void main(String[] args) {

        Logger logger = new Logger();
        AudioBackEnd b = new AudioBackEnd(logger);
        b.setFile(new File("src/test/resources/SingingBowl.wav"));
        b.setIfFileFormatSupported();
        b.setIfAudioInputLineAvailable();
        BasicControlsGUIExecutorV1 a = new BasicControlsGUIExecutorV1(b, logger);
        BasicControlsGUI gui = new BasicControlsGUI(a);
        FileSelectorGUI fgui = new FileSelectorGUI();

        AMPGUI ampgui = new AMPGUI(null, gui, fgui, null, null);

        SwingUtilities.invokeLater(
                () -> {ampgui.execute();}
        );

    }
}
