package tvnoty.core.database.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "subscribers")
public class Subscriber {
    private final Set<String> imdbCodes;

    @Id
    private String id;

    private String email;

    public Subscriber() {
        imdbCodes = new HashSet<>();
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
            this.imdbCodes.add(imdbCode);
        }
    }

    public void unsubscribe(final List<String> imdbCodes) {
        for (final String imdbCode : imdbCodes)
            while (this.imdbCodes.remove(imdbCode)) {
            }
    }

    public void unsubscribeAll() {
        imdbCodes.clear();
    }

    public Set<String> getSubscriptions() {
        return imdbCodes;
    }
}
