package tvnoty.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum RESTResponseEnum {
    INVALID_EMAIL(100, "Email address is invalid."),
    INVALID_SUBSCRIBE_LIST(101, "Subscribe list is badly formed."),
    INVALID_UNSUBSCRIBE_LIST(102, "Unsubuscribe list is badly formed."),
    USER_NOT_FOUND(102, "Requested user not found."),
    INTERNAL_SERVER_ERROR(500, "Server cannot process your request."),
    SUBSCRIBE_OK(200, "Subscribe operation successful."),
    UNSUBSCRIBE_OK(201, "Unsubscribe operation successful.");

    private final Integer code;
    private final String message;

    RESTResponseEnum(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public RESTResponse getMessage() {
        return new RESTResponse(code, message);
    }
}
