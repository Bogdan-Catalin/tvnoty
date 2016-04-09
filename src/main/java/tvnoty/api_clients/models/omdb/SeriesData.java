package tvnoty.api_clients.models.omdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tvnoty.core.database.entities.Series;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesData {
    private String title;
    private String year;
    private String imdbID;
    private String imdbRating;
    private String plot;
    private String poster;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public Series toMongoEntity() {
        final Series series = new Series();
        series.setImdb_id(imdbID);
        series.setImdb_rating(imdbRating);
        series.setPlot(plot);
        series.setPoster(poster);
        series.setTitle(title);
        series.setYear(year);
        return series;
    }
}
