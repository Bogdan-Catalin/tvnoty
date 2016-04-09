package tvnoty.api_clients.consumers;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import tvnoty.api_clients.models.omdb.SeasonData;
import tvnoty.api_clients.models.omdb.SeriesData;


public interface OmdbAPIClientInterface {
    @GET("/")
    Call<SeriesData> getSeriesData(@Query("i") String imdbId);

    @GET("/")
    Call<SeasonData> getSeasonData(@Query("i") String imdbId, @Query("Season") Integer seasonNo);
}
