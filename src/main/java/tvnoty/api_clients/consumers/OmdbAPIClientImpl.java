package tvnoty.api_clients.consumers;


import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.stereotype.Service;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import tvnoty.api_clients.models.omdb.SeasonData;
import tvnoty.api_clients.models.omdb.SeriesData;

import java.io.IOException;

@Service
public class OmdbAPIClientImpl {
    private final OmdbAPIClientInterface client;

    public OmdbAPIClientImpl() {
        final ObjectMapper om = new ObjectMapper();
        om.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        om.registerModule(new JodaModule());

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(JacksonConverterFactory.create(om))
                .build();

        client = retrofit.create(OmdbAPIClientInterface.class);
    }

    public SeriesData getSeriesData(final String imdbId) throws IOException {
        return client.getSeriesData(imdbId).execute().body();
    }

    public SeasonData getSeasonData(final String imdbId, final Integer seasonNo) throws IOException {
        return client.getSeasonData(imdbId, seasonNo).execute().body();
    }
}
