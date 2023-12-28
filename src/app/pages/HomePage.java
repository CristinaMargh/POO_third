package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The type Home page.
 */
public final class HomePage implements Page {
    private List<Song> likedSongs;
    private List<Playlist> followedPlaylists;
    private List<Song> suggestions;
    private List<Playlist> suggestionsPlaylist;
    private final int limit = 5;
    private String username = null;
    private List<String> playlistRecommendationName ;
    private Page nextPage;
    private Page previousPage;

    /**
     * Instantiates a new Home page.
     *
     * @param user the user
     */
    public HomePage(final User user) {
        username = user.getUsername();
        likedSongs = user.getLikedSongs();
        followedPlaylists = user.getFollowedPlaylists();
        suggestions = user.getSongRecommendation();
        suggestionsPlaylist = user.getPlaylistRecommendation();
        playlistRecommendationName = user.getPlaylistsRecommendationName();
    }

    @Override
    public String printCurrentPage() {
        if (suggestions.isEmpty() && playlistRecommendationName.isEmpty()) {
            return "Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s"
                   .formatted(likedSongs.stream()
                                        .sorted(Comparator.comparing(Song::getLikes)
                                        .reversed()).limit(limit).map(Song::getName)
                              .toList(),
                              followedPlaylists.stream().sorted((o1, o2) ->
                                      o2.getSongs().stream().map(Song::getLikes)
                                        .reduce(Integer::sum).orElse(0)
                                      - o1.getSongs().stream().map(Song::getLikes).reduce(Integer::sum)
                                      .orElse(0)).limit(limit).map(Playlist::getName)
                              .toList());
        } else {
            if(playlistRecommendationName.isEmpty())
                return ("Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s\n\nSong recommendations:\n\t%s\n\nPlaylists" +
                    " recommendations:\n\t[]")
                    .formatted(likedSongs.stream()
                                    .sorted(Comparator.comparing(Song::getLikes)
                                            .reversed()).limit(limit).map(Song::getName)
                                    .toList(),
                            followedPlaylists.stream().sorted((o1, o2) ->
                                            o2.getSongs().stream().map(Song::getLikes)
                                                    .reduce(Integer::sum).orElse(0)
                                                    - o1.getSongs().stream().map(Song::getLikes).reduce(Integer::sum)
                                                    .orElse(0)).limit(limit).map(Playlist::getName)
                                    .toList(), suggestions.stream().limit(1).map(Song::getName).sorted().toList());
            else
                return ("Liked songs:\n\t%s\n\nFollowed playlists:\n\t%s\n\nSong recommendations:\n\t%s\n\nPlaylists" +
                        " recommendations:\n\t%s")
                        .formatted(likedSongs.stream()
                                        .sorted(Comparator.comparing(Song::getLikes)
                                                .reversed()).limit(limit).map(Song::getName)
                                        .toList(),
                                followedPlaylists.stream().sorted((o1, o2) ->
                                                o2.getSongs().stream().map(Song::getLikes)
                                                        .reduce(Integer::sum).orElse(0)
                                                        - o1.getSongs().stream().map(Song::getLikes).reduce(Integer::sum)
                                                        .orElse(0)).limit(limit).map(Playlist::getName)
                                        .toList(), suggestions.stream().limit(1).map(Song::getName).sorted().toList(),
                                playlistRecommendationName.stream().limit(1).toList());
        }
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
