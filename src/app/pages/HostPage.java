package app.pages;

import app.audio.Collections.Podcast;
import app.user.Announcement;
import app.user.Host;

import java.util.List;

/**
 * The type Host page.
 */
public final class HostPage implements Page {
    private List<Podcast> podcasts;
    private List<Announcement> announcements;
    private Page nextPage;
    private Page previousPage;
    private String username;

    /**
     * Instantiates a new Host page.
     *
     * @param host the host
     */
    public HostPage(final Host host) {
        username = host.getUsername();
        podcasts = host.getPodcasts();
        announcements = host.getAnnouncements();
    }

    @Override
    public String printCurrentPage() {
        return "Podcasts:\n\t%s\n\nAnnouncements:\n\t%s"
               .formatted(podcasts.stream().map(podcast -> "%s:\n\t%s\n"
                          .formatted(podcast.getName(),
                                     podcast.getEpisodes().stream().map(episode -> "%s - %s"
                          .formatted(episode.getName(), episode.getDescription())).toList()))
                          .toList(),
                          announcements.stream().map(announcement -> "%s:\n\t%s\n"
                          .formatted(announcement.getName(), announcement.getDescription()))
                          .toList());
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
