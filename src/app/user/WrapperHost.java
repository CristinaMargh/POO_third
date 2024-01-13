package app.user;

import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public final class WrapperHost {
    private List<Episode> topEpisodes = new ArrayList<>();

    /**
     * Used to create the wrapped statistics for a host(topEpisodes).
     * @param host the host for which I do the statistics
     */
    public void wrapperHost(final Host host) {
        this.topEpisodes = host.getListenedEpisodes();
    }


}
