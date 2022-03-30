package P_Practices;

public class MainApp {

    public static void main(String[] args) {

        String fileName = "resources/SingingBowl.wav";
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new P1_Small_Swing_Player(fileName).minimalPlayer();
            }
        });
    }
}
