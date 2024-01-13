package app.player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements  ShuffleStrategy {
    @Override
    public void shuffleOrder(final List<Integer> indices, final int numberOfTracks) {
        Random random = new Random();
        for (int i = 0; i < numberOfTracks; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
    }
}

