package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.List;


/**
 * The type Liked content page.
 */
public final class LikedContentPage implements Page {
    private Page nextPage;
    private Page previousPage;
    private String username;
    /**
     * The Liked songs.
     */
    private List<Song> likedSongs;
    /**
     * The Followed playlists.
     */
    private List<Playlist> followedPlaylists;

    /**
     * Instantiates a new Liked content page.
     *
     * @param user the user
     */
    public LikedContentPage(final User user) {
        likedSongs = user.getLikedSongs();
        username = user.getUsername();
        followedPlaylists = user.getFollowedPlaylists();
    }

    @Override
    public String printCurrentPage() {
        return "Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s"
               .formatted(likedSongs.stream().map(song -> "%s - %s"
                          .formatted(song.getName(), song.getArtist())).toList(),
                          followedPlaylists.stream().map(playlist -> "%s - %s"
                          .formatted(playlist.getName(), playlist.getOwner())).toList());
    }
    /**
     * Used to change the current page to a new one
     * @param page the page object to set as the next one
     */
    public void changePage(final Page page) {
        nextPage = page;
    }

}
