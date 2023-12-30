package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.*;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * The type User.
 */
public final class User extends UserAbstract {
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private final Player player;
    @Getter
    private boolean status;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    @Setter
    private Page currentPage;
    @Getter
    private PageFactory.PageType currentPageType;
    @Getter
    @Setter
    private HomePage homePage;
    @Getter
    @Setter
    private LikedContentPage likedContentPage;
    @Getter
    @Setter
    private Page artistPage;
    @Getter
    @Setter
    private Page hostPage;
    private List<Song> songsSameGenre = new ArrayList<>();
    private List<Merchandise> boughtMerch = new ArrayList<>();
    @Getter
    private List<Song> songRecommendation = new ArrayList<>();
    @Getter
    private List<Playlist> playlistRecommendation = new ArrayList<>();
    @Getter
    private List<String> playlistsRecommendationName = new ArrayList<>();
    @Getter
    @Setter
    private List<Page> pages = new ArrayList<>();
    @Getter
    private LinkedList<Page> history = new LinkedList<>();
    @Getter
    private List<Song> listenedSongs = new ArrayList<>();
    @Getter
    private List<Episode> listenedEpisodes = new ArrayList<>();
    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        super(username, age, city);
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        status = true;

        homePage = new HomePage(this);
        currentPage = homePage;
        pages.add(homePage);
        history.push(homePage);
        likedContentPage = new LikedContentPage(this);
    }

    @Override
    public String userType() {
        return "user";
    }
    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();

        if (type.equals("artist") || type.equals("host")) {
            List<ContentCreator> contentCreatorsEntries =
            searchBar.searchContentCreator(filters, type);

            for (ContentCreator contentCreator : contentCreatorsEntries) {
                results.add(contentCreator.getUsername());
            }
        } else {
            List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

            for (LibraryEntry libraryEntry : libraryEntries) {
                results.add(libraryEntry.getName());
            }
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        if (searchBar.getLastSearchType().equals("artist")
            || searchBar.getLastSearchType().equals("host")) {
            ContentCreator selected = searchBar.selectContentCreator(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }

            currentPage = selected.getPage();
            pages.add(currentPage);
            return "Successfully selected %s's page.".formatted(selected.getUsername());
        } else {
            LibraryEntry selected = searchBar.select(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }

            return "Successfully selected %s.".formatted(selected.getName());
        }
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }
        if (!searchBar.getLastSearchType().equals("song")
            && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());

        Song song = null;
        if (searchBar.getLastSearchType().equals("song")) {
            song = (Song) player.getSource().getAudioFile();
            getListenedSongs().add(song);
            if (song != null)
                song.setListens(song.getListens() + 1);

            for (Artist artist : Admin.getInstance().getArtists()) {
                if (song != null && artist.getUsername().equals(song.getArtist())  && !artist.getFans().contains(this)) {
                    artist.setLoadsNumber(artist.getLoadsNumber() + 1);
                    artist.getFans().add(this);
                    artistPage = artist.getPage();
                    break;
                }
                for (Album album : artist.getAlbums()) {
                    if (album.containsTrack(song)) {
                        album.setNumberOfListens(album.getNumberOfListens() + 1);
                    }
                }
            }
        }
        Album album = null;
        if (searchBar.getLastSearchType().equals("album")) {
           album = (Album)player.getSource().getAudioCollection();
                player.getSource().updateAudioFile();
               album.setNumberOfListens(album.getNumberOfListens() + 1);
            for (Song song1 : album.getSongs()) {
                song1.setListens(song1.getListens() + 1);
            }
        }

        // Used for changePage
        Podcast podcast = null;
        if (searchBar.getLastSearchType().equals("podcast")) {
            podcast = (Podcast) player.getSource().getAudioCollection();

            // Iterate through all hosts
            for (Host host : Admin.getInstance().getHosts()) {
                // find the host which has the podcast
                if (host.getUsername().equals(podcast.getOwner())) {
                    hostPage = host.getPage();
                    int duration = 0;

                    // total duration of the podcast's episodes
                    for (Episode episode : podcast.getEpisodes()) {
                        duration += episode.getDuration();

                        // listens for the current episode
                        if ( episode.getName().equals(player.getSource().getAudioFile().getName())) {
                            getListenedEpisodes().add(episode);
                            episode.setListens(episode.getListens() + 1);
                            break;
                        }
                    }
                    // Calculate for the rest episodes
                    int remain = duration - this.getPlayerStats().getRemainedTime();
                    int total = 0;

                    for (Episode episode : podcast.getEpisodes()) {
                        // check not to go over the total time
                        if (episode.getDuration() + total <= remain) {
                            // sets for host and episodes
                            episode.setListens(episode.getListens() + 1);
                            host.getListenedEpisodes().add(episode);
                            total += episode.getDuration();
                        } else {
                            break; //over the time limit
                        }
                    }
                }
            }
        }
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }
    public String loadRecommendation() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }
        if (songRecommendation != null && !songRecommendation.isEmpty()) {
            player.setSource(songRecommendation.get(0), "song");
            for (Artist artist : Admin.getInstance().getArtists())
                if (artist.getUsername().equals(songRecommendation.get(0).getArtist())) {
                    int number = artist.getLoadsNumber();
                    artist.setLoadsNumber(number + 1);
                }
            player.pause();
            return "Playback loaded successfully.";
        } else {
            return "No recommendations available.";
        }
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist")
            && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
            && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(getUsername())) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Switch status.
     */
    public void switchStatus() {
        status = !status;
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        if (!status) {
            return;
        }
        player.simulatePlayer(time);
    }
    public String subscribe(CommandInput commandInput) {
        ContentCreator selection = searchBar.getLastContentCreatorSelected();
        UserAbstract userAbstract = Admin.getInstance().getAbstractUser(commandInput.getUsername());
        if ((searchBar.getLastSearchType().equals("artist")
                || searchBar.getLastSearchType().equals("host")) && selection != null) {
            // If it's already subscribed
            if (getCreators().contains(selection)) {
                getCreators().remove(selection);
                return userAbstract.getUsername() + " unsubscribed from " + selection.getUsername() + " successfully.";
            } else {
                getCreators().add(selection);
                return userAbstract.getUsername() + " subscribed to " + selection.getUsername() + " successfully.";
            }
        } else if(!searchBar.getLastSearchType().equals("artist") && !searchBar.getLastSearchType().equals("host")) {
            return "You can subscribe only to artists and hosts.";
        }

        return "To subscribe you need to be on the page of an artist or host.";
    }
    public String buyMerch(CommandInput commandInput) {
        ContentCreator selection = searchBar.getLastContentCreatorSelected();
        if((!searchBar.getLastSearchType().equals("artist")))
            return "Cannot buy merch from this page.";
        Artist currentArtist = (Artist) selection;
        Optional<Merchandise> matchingMerch = currentArtist.getMerch().stream()
                .filter(merch -> merch.getName().equals(commandInput.getName()))
                .findFirst();

        if (matchingMerch.isPresent()) {
            boughtMerch.add(matchingMerch.get());
            double revenue = currentArtist.getMerchRevenue();
            currentArtist.setMerchRevenue(revenue + matchingMerch.get().getPrice());
            return this.getUsername() + " has added new merch successfully.";
        } else {
            return "The merch " + commandInput.getName() + " doesn't exist.";
        }
    }
    public List<String> seeMerch() {
        List<String> merchNames = new ArrayList<>();
        for (Merchandise merchandise : boughtMerch)
            merchNames.add(merchandise.getName());
        return merchNames;
    }
    public String updateRecommendation(CommandInput commandInput) {
        if (commandInput.getRecommendationType().equals("random_song")) {
            int remain = this.getPlayerStats().getRemainedTime();
            Song ourSong = null;
            Random random = null;

            for (Song song : Admin.getInstance().getSongs()) {
                if (this.getPlayer().getSource().getAudioFile().getName().equals(song.getName()) && song.getDuration() - remain >= 30) {
                    ourSong = song;
                    random = new Random(song.getDuration() - remain);
                    break;
                }
            }
            Song randomSong = null;
            if (ourSong != null) {
                for (Song song : Admin.getInstance().getSongs()) {
                    if (ourSong.getGenre().equals(song.getGenre())) {
                        this.songsSameGenre.add(song);
                    }
                }
                if (!this.songsSameGenre.isEmpty()) {
                    int randomIndex = random.nextInt(this.songsSameGenre.size());
                    randomSong = this.songsSameGenre.get(randomIndex);
                    if (randomSong != null) {
                        this.songRecommendation.add(randomSong);
                    }
                }
            }
        } else if(commandInput.getRecommendationType().equals("random_playlist")) {
            playlistsRecommendationName.add(getUsername() +"'s recommendations");
        } else if(commandInput.getRecommendationType().equals("fans_playlist")) {
            Song currentSong = null;
            for(Song song : Admin.getInstance().getSongs())
                if(song.getName().equals(this.getPlayer().getSource().getAudioFile().getName())) {
                    currentSong = song;
                    playlistsRecommendationName.add(currentSong.getArtist() + " Fan Club recommendations");
                }
        }
        return "The recommendations for user " + this.getUsername()+ " have been updated successfully.";
    }
    public String buyPremium() {
        return  this.getUsername() + " bought the subscription successfully.";
    }
    public String cancelPremium() {
        return  this.getUsername() + " cancelled the subscription successfully.";
    }

}
