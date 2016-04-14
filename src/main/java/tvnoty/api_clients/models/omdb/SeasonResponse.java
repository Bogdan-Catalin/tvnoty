package tvnoty.api_clients.models.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonResponse {
    private String title;
    private Integer season;
    private List<EpisodeResponse> episodes;

    public String getTitle() {
        return title;
    }

    public Integer getSeason() {
        return season;
    }

    public List<EpisodeResponse> getEpisodes() {
        return episodes;
    }
}
