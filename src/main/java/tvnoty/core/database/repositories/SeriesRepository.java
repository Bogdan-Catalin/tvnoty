package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tvnoty.core.database.entities.Series;

public interface SeriesRepository extends MongoRepository<Series, String> {
}
