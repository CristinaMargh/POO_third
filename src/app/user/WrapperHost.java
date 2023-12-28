package app.user;

import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class WrapperHost {
    List<Episode> topEpisodes = new ArrayList<>();
    public void wrapperHost(Host host) {
        this.topEpisodes = host.getAllEpisodes();
    }
}
