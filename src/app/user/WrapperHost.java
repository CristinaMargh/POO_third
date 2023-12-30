package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.searchBar.SearchBar;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class WrapperHost {
    List<Episode> topEpisodes = new ArrayList<>();
    public void wrapperHost(Host host1) {
        this.topEpisodes = host1.getListenedEpisodes();
    }


}
