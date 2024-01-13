package app.user;

import lombok.Getter;
@Getter
public class EndProgramOutput {
    private final String artistName;
    private double songRevenue;
    private final double merchRevenue;
    private final int ranking;
    private final String mostProfitableSong;
    public EndProgramOutput(final Artist artist) {
        this.artistName = artist.getUsername();
        this.merchRevenue = artist.getMerchRevenue();
        this.ranking = artist.getRank();
        this.mostProfitableSong = artist.getMostProfitableSong();
    }
}
