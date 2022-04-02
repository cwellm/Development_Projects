package minimalisticPlayer;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Utility class supplying a listener for the audio streaming procedures
 */
public class AudioLineListener implements LineListener {
    @Override
    public void update(LineEvent lineEvent) {
        System.out.println("LINE LISTENER SAYS:");
        System.out.println(lineEvent.toString());
    }
}
