package app.player;

import java.util.List;

public interface ShuffleStrategy {
    /**
     * Used to initialize and keep the normal order of the tracks from the AudioCollection
     * @param indices is the list with the indices of the files from the collection.
     * @param numberOfTracks represents the number of audio files
     */
    void normalOrder(List<Integer> indices, int numberOfTracks);
    /**
     * Used to shuffle the indices and recreate a new order for them
     * @param indices is the list with the indices of the files from the collection.
     * @param numberOfTracks represents the number of files from the audio collection
     * @param seed is used for the random mixture
     */
    void shuffleOrder(List<Integer> indices, int numberOfTracks, int seed);
}
