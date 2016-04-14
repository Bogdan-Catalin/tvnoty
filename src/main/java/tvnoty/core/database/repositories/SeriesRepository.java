package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tvnoty.core.database.entities.Series;

@Repository
public interface SeriesRepository extends MongoRepository<Series, String> {
    @Query("{'_id' : ?0}")
    Series findByImdbId(final String imdbId);
}
