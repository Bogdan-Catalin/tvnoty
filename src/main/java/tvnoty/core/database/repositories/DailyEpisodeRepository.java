package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tvnoty.core.database.entities.DailyEpisode;

public interface DailyEpisodeRepository extends MongoRepository<DailyEpisode, String> {
}
