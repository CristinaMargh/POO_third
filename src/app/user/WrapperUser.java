package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public final class WrapperUser {
    private List<Artist> topArtist = new ArrayList<>();
    private List<String> topGenre = new ArrayList<>();
    private List<Song> topSongs = new ArrayList<>();
    private List<Album> topAlbums = new ArrayList<>();
    private List<Episode> topEpisodes = new ArrayList<>();

    /**
     * Used to create the statistic for a normal user
     * @param user the user whose preferences we want to know
     */
    public void wrapperUser(final User user) {
        this.topGenre = user.getTopGenre();
        this.topArtist = user.getTopArtists();
        this.topSongs = user.getListenedSongs();
        this.topEpisodes = user.getListenedEpisodes();
        this.topAlbums = user.getTopAlbums();
    }
}
