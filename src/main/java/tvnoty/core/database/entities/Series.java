package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tvnoty.api.models.omdb.SeasonData;

import java.util.List;

@Document(collection = "series")
public class Series {
    @Id
    private String imdbID;

    private String title;
    private String year;
    private String imdbRating;
    private String plot;
    private String poster;

    private List<SeasonData> seasons;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(final String imdbID) {
        this.imdbID = imdbID;
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

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(final String imdbRating) {
        this.imdbRating = imdbRating;
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
