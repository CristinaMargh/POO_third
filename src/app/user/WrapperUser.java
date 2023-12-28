package app.user;

import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class WrapperUser {
    List<Artist> topArtist = new ArrayList<>();
    List<String> topGenre = new ArrayList<>();
    List<Song> topSongs = new ArrayList<>();
    List<Album> topAlbums = new ArrayList<>();
    List<Episode> topEpisodes = new ArrayList<>();
    public void wrapperUser(User user) {
        this.topSongs = user.getListenedSongs();

    }
}
