package Controller;

import java.util.ArrayList;

public interface IFileSelectorDispatcher {

    void setToNextListPosition();
    void setToListPosition(int listPosition);

    int getCurrentPlayingIndex();

    ArrayList<Integer> getSelectedIndices();
}
