package tvnoty.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TvnotyUtils {
    // Object because I like to live dangerously
    public static String mapObjectToRESTResponse(final ObjectMapper mapper, final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (final JsonProcessingException e) {
            return "{\"code\" : 500, \"message\": \"Server cannot process your request.\"}";
        }
    }
}
