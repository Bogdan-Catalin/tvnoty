package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Generated;

@Document(collection = "daily_episodes")
public class DailyEpisode {
    @Id
    @Generated(value = "AUTO")
    private String id;

    private String series_imdb_id;

    private String series_name;

    private Integer season;

    private Integer episode;

    public String getSeries_imdb_id() {
        return series_imdb_id;
    }

    public void setSeries_imdb_id(final String series_imdb_id) {
        this.series_imdb_id = series_imdb_id;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(final String series_name) {
        this.series_name = series_name;
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
