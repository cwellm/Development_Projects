package minimalisticPlayer;

/**
 * Class for starting the player. Calls the Swing thread and opens the swing window with all functionality.
 */
// @TODO Add slider to modify the position within the music file
public class MainApp {

    public static void main(String[] args) {

        String fileName = "resources/SingingBowl.wav";
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MinimalisticPlayerFrontend().execute();
            }
        });
    }
}
