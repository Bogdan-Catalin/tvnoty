package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tvnoty.core.database.entities.Series;

@Repository
public interface SeriesRepository extends MongoRepository<Series, String> {
}
