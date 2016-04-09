package tvnoty.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum ErrorResponseEnum {
    INVALID_EMAIL(100, "Email address is invalid."),
    INVALID_SUBSCRIBE_LIST(101, "Subscribe list is badly formed."),
    INVALID_UNSUBSCRIBE_LIST(102, "Unsubuscribe list is badly formed."),
    INTERNAL_SERVER_ERROR(500, "Server cannot process your request.");

    private final Integer code;
    private final String message;

    ErrorResponseEnum(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    @JsonValue
    public ErrorResponse getMessage() {
        return new ErrorResponse(code, message);
    }
}
