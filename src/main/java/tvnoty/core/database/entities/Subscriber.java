package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "subscribers")
public class Subscriber {
    private Set<String> imdb_codes;

    @Id
    private String id;

    private String email;

    public Subscriber() {
        imdb_codes = new HashSet<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void subscribe(final List<String> imdbCodes) {
        for (final String imdbCode : imdbCodes) {
            this.imdb_codes.add(imdbCode);
        }
    }

    public void unsubscribe(final List<String> imdbCodes) {
        for (final String imdbCode : imdbCodes)
            while (this.imdb_codes.remove(imdbCode)) {
            }
    }

    public void unsubscribeAll() {
        imdb_codes.clear();
    }

    public void setImdb_codes(final Set<String> imdb_codes) {
        this.imdb_codes = imdb_codes;
    }

    public Set<String> getSubscriptions() {
        return imdb_codes;
    }

}
