package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tvnoty.core.database.entities.DailyEpisode;

@Repository
public interface DailyEpisodeRepository extends MongoRepository<DailyEpisode, String> {
}
