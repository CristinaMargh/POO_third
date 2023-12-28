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
    public void changePage(Page page) {
        nextPage = page;
    }

    public void nextPage() {
        if (nextPage != null) {
            System.out.println("The user " +  username + " has navigated successfully to the next page.");

        } else {
            System.out.println("There are no pages left to go forward.");
        }
    }

    public void previousPage() {
        if (previousPage != null) {
            System.out.println("The user" +  username + " has navigated successfully to the previous page.");
        } else {
            System.out.println("There are no pages left to go back.");
        }
    }
}
