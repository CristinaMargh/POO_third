package app.user;

import lombok.Getter;

public class endProgramOutput {
    @Getter
    String artistName;
    @Getter
    double songRevenue;
    @Getter
    double merchRevenue;
    @Getter
    int ranking;
    @Getter
    String mostProfitableSong;
    public endProgramOutput(Artist artist) {
        this.artistName = artist.getUsername();
        this.merchRevenue = artist.getMerchRevenue();
        this.ranking = artist.getRank();
        this.mostProfitableSong = artist.getMostProfitableSong();
    }
}
