package tvnoty.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tvnoty.core.database.entities.Subscriber;
import tvnoty.core.database.repositories.SubscriberRepository;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    SubscriberRepository subscriberRepository;

    public void subscribe(final String email, final List<String> imdbCodes) {
        Subscriber subscriber = getUserForEmail(email);
        if (subscriber == null) {
            subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriber.subscribe(imdbCodes);
            subscriberRepository.insert(subscriber);
        } else {
            subscriber.subscribe(imdbCodes);
            subscriberRepository.save(subscriber);
        }
    }

    public void unsubscribe(final String email, final List<String> imdbCodes) {
        final Subscriber subscriber = getUserForEmail(email);
        if (subscriber != null) {
            subscriber.unsubscribe(imdbCodes);
            subscriberRepository.save(subscriber);
        }
    }

    private Subscriber getUserForEmail(final String email) {
        return subscriberRepository.findAll().stream().filter(x -> x.getEmail().toLowerCase().equals(email.toLowerCase())).findFirst().get();
    }
}
