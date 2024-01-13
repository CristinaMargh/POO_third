package app.player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomStrategy implements  ShuffleStrategy {
    /**
     * Used to shuffle the indices and recreate a new order for them
     * @param indices is the list with the indices of the files from the collection.
     * @param numberOfTracks represents the number of files from the audio collection
     * @param seed is used for the random mixture
     */
    @Override
    public void shuffleOrder(final List<Integer> indices, final int numberOfTracks,
                             final int seed) {
        Random random = new Random(seed);
        for (int i = 0; i < numberOfTracks; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
    }

    /**
     * Used to initialize and keep the normal order of the tracks from the AudioCollection
     * @param indices is the list with the indices of the files from the collection.
     * @param numberOfTracks represents the number of audio files
     */
    public void normalOrder(final List<Integer> indices, final int numberOfTracks) {
        for (int i = 0; i < numberOfTracks; i++) {
            indices.add(i);
        }
    }
}

