package app.user;

import app.audio.Collections.Album;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WrapperArtist {
    List<Album> topAlbums = new ArrayList<>();
    List<Song> topSongs = new ArrayList<>();
    List<UserAbstract> topFans = new ArrayList<>();
    public void wrapperArtist(Artist artist) {
        this.topAlbums = artist.getAlbums();
        this.topSongs = artist.getAllSongs();
        this.topFans = artist.getFans();
    }
}
