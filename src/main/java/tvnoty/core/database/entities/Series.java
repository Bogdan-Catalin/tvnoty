package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tvnoty.api_clients.models.omdb.SeasonData;

import java.util.List;

@Document(collection = "series")
public class Series {
    @Id
    private String imdb_id;

    private String title;
    private String year;
    private String imdb_rating;
    private String plot;
    private String poster;

    private List<SeasonData> seasons;

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(final String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public String getImdb_rating() {
        return imdb_rating;
    }

    public void setImdb_rating(final String imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(final String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(final String poster) {
        this.poster = poster;
    }

    public List<SeasonData> getSeasons() {
        return seasons;
    }

    public void setSeasons(final List<SeasonData> seasons) {
        this.seasons = seasons;
    }
}
