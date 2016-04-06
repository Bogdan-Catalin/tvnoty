package tvnoty.core.database.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tvnoty.core.database.entities.Subscriber;

@Repository
public interface SubscriberRepository extends MongoRepository<Subscriber, String> {
}
