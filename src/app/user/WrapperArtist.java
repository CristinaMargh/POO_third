package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class WrapperArtist {
    private List<Album> topAlbums = new ArrayList<>();
    private List<Song> topSongs = new ArrayList<>();
    private List<UserAbstract> topFans = new ArrayList<>();

    /**
     * Used for the artist's statistics
     * @param artist the artist for whom we are performing these charts
     */
    public void wrapperArtist(final Artist artist) {
        this.topAlbums = artist.getAlbums();
        this.topSongs = artist.getListenedSongs();
        this.topFans = artist.getFans();
    }
}
