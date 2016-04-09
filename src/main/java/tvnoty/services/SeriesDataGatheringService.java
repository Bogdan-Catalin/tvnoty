package tvnoty.services;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tvnoty.api_clients.consumers.OmdbAPIClientImpl;
import tvnoty.api_clients.models.omdb.EpisodeData;
import tvnoty.api_clients.models.omdb.SeasonData;
import tvnoty.api_clients.models.omdb.SeriesData;
import tvnoty.core.database.entities.Series;
import tvnoty.core.database.entities.Subscriber;
import tvnoty.core.database.repositories.SeriesRepository;
import tvnoty.core.database.repositories.SubscriberRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SeriesDataGatheringService {
    private static final Logger LOGGER = Logger.getLogger(SeriesDataGatheringService.class.getName());

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private OmdbAPIClientImpl omdbAPIClient;

    public void pullData() {
        final Set<String> imdbCodes = extractImdbIds();
        // For every series
        for (final String imdbCode : imdbCodes) {
            try {
                LOGGER.info("Getting data for series with imdb id: " + imdbCode);
                final Series series = getFromAPI(imdbCode);
                if (series != null) {
                    saveToDb(series);
                }
            } catch (final IOException e) {
                LOGGER.error("Failed to get data", e);
            }
        }
    }

    public void pullSpecificData(final Set<String> imdbCodes) {
        LOGGER.info("Starting to gather specific series data.");
        for (final String imdbCode : imdbCodes) {
            try {
                LOGGER.info("Getting data for series with imdb id: " + imdbCode);
                final Series series = getFromAPI(imdbCode);
                if (series != null) {
                    saveToDb(series);
                }
            } catch (final IOException e) {
                LOGGER.error("Failed to get data", e);
            }
        }
    }

    private void saveToDb(final Series series) {
        if (seriesRepository.findOne(series.getImdb_id()) == null) {
            seriesRepository.insert(series);
        } else {
            final Series existing = seriesRepository.findOne(series.getImdb_id());
            series.getSeasons().addAll(existing.getSeasons());
            seriesRepository.save(series);
        }
    }

    private Series getFromAPI(final String imdbId) throws IOException {
        final SeriesData seriesData = omdbAPIClient.getSeriesData(imdbId);
        if (seriesData.getImdbID() == null) {
            return null;
        }
        final List<SeasonData> seasons = new ArrayList<>();
        for (Integer i = getSeasonToPull(imdbId); ; i++) {
            final SeasonData sd = omdbAPIClient.getSeasonData(imdbId, i);
            if (sd.getEpisodes() == null) {
                break;
            } else {
                seasons.add(sd);
            }
        }

        final Series series = seriesData.toMongoEntity();
        series.setSeasons(seasons);
        return series;
    }

    private Set<String> extractImdbIds() {
        final List<Subscriber> subs = subscriberRepository.findAll();
        final Set<String> result = new HashSet<>();
        for (final Subscriber s : subs) {
            for (final String imdbCode : s.getSubscriptions()) {
                result.add(imdbCode);
            }
        }
        return result;
    }

    private int getSeasonToPull(final String imdbId) {
        final Series series = seriesRepository.findOne(imdbId);
        if (series != null) {
            if (series.getSeasons().isEmpty()) {
                return 1;
            } else {
                for (int i = 1; i < series.getSeasons().size(); i++) {
                    for (final EpisodeData ed : series.getSeasons().get(i).getEpisodes()) {
                        // TODO: use proper timestamp check to see what release is not announced
                        // TODO: do series release date vary ? 
                        if (ed.getReleased().toLowerCase().equals("n/a")) {
                            return i;
                        }
                    }
                }
                return (series.getSeasons().size());
            }
        } else {
            return 1;
        }
    }
}
