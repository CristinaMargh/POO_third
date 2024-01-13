package app.player;

import java.util.List;

public interface ShuffleStrategy {
    void shuffleOrder(List<Integer> indices, int numberOfTracks);
}
