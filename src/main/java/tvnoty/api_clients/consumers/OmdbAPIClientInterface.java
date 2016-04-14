package tvnoty.api_clients.consumers;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import tvnoty.api_clients.models.omdb.SeasonResponse;
import tvnoty.api_clients.models.omdb.SeriesResponse;


public interface OmdbAPIClientInterface {
    @GET("/")
    Call<SeriesResponse> getSeriesData(@Query("i") String imdbId);

    @GET("/")
    Call<SeasonResponse> getSeasonData(@Query("i") String imdbId, @Query("Season") Integer seasonNo);

    @GET("/")
    Call<SeasonResponse> getEpisodeData(@Query("i") String imdbId, @Query("Season") Integer seasonNo, @Query("Episode") Integer episodeNo);
}
