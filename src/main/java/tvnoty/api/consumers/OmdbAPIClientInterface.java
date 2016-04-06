package tvnoty.api.consumers;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import tvnoty.api.models.omdb.SeasonData;
import tvnoty.api.models.omdb.SeriesData;


public interface OmdbAPIClientInterface {
    @GET("/")
    Call<SeriesData> getSeriesData(@Query("i") String imdbId);

    @GET("/")
    Call<SeasonData> getSeasonData(@Query("i") String imdbId, @Query("Season") Integer seasonNo);
}
