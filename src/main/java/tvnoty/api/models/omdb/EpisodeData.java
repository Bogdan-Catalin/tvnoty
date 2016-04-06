package tvnoty.api.models.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EpisodeData {
    private String title;
    private String released;
    private String imdbRating;
    private Integer episode;

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public Integer getEpisode() {
        return episode;
    }
}
