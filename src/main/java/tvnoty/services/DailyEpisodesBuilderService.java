package tvnoty.services;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tvnoty.api_clients.models.omdb.EpisodeData;
import tvnoty.api_clients.models.omdb.SeasonData;
import tvnoty.core.database.entities.DailyEpisode;
import tvnoty.core.database.entities.Series;
import tvnoty.core.database.repositories.DailyEpisodeRepository;
import tvnoty.core.database.repositories.SeriesRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyEpisodesBuilderService {
    private static final Logger LOGGER = Logger.getLogger(DailyEpisodesBuilderService.class.getName());

    @Autowired
    private DailyEpisodeRepository dailyRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    public void buildDailyEpisodeList() {
        dailyRepository.deleteAll();
        final List<DailyEpisode> dailyEpisodes = new ArrayList<>();
        final List<Series> series = seriesRepository.findAll();
        for (final Series serie : series) {
            for (final SeasonData season : serie.getSeasons()) {
                boolean breakSeason = false;
                for (final EpisodeData episode : season.getEpisodes()) {
                    if (!episode.getReleased().toLowerCase().equals("n/a")) {
                        final DateTime current = new DateTime();
                        final DateTime episodeRelease = new DateTime(episode.getReleased());
                        if (Days.daysBetween(current.withTimeAtStartOfDay(), episodeRelease.withTimeAtStartOfDay()).getDays() == 1) {
                            final DailyEpisode de = new DailyEpisode();
                            de.setEpisode(episode.getEpisode());
                            de.setSeason(season.getSeason());
                            de.setSeries_imdb_id(serie.getImdb_id());
                            de.setSeries_name(serie.getTitle());

                            // TODO: populate with more data
                            dailyEpisodes.add(de);
                        }
                    } else {
                        breakSeason = true;
                        break;
                    }
                }
                if (breakSeason) {
                    break;
                }
            }
        }
        dailyRepository.insert(dailyEpisodes);
        LOGGER.info("Finished building daily episodes list.");
    }
}
