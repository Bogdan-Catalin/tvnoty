package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;

@Document(collection = "daily_episodes")
public class DailyEpisode {
    @Id
    @Generated(value = "AUTO")
    private String id;

    private String seriesImdbId;

    private String seriesName;

    private Integer season;

    private Integer episode;

    public String getSeriesImdbId() {
        return seriesImdbId;
    }

    public void setSeriesImdbId(final String seriesImdbId) {
        this.seriesImdbId = seriesImdbId;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(final String seriesName) {
        this.seriesName = seriesName;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(final Integer season) {
        this.season = season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(final Integer episode) {
        this.episode = episode;
    }
}
