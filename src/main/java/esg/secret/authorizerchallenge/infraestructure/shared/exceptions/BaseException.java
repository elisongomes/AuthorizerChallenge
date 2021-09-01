package esg.secret.authorizerchallenge.infraestructure.shared.exceptions;

import java.util.List;

public abstract class BaseException extends Exception {
    private final int code;
    private final List<String> messages;

    public BaseException(final int code, final String message) {
        super(message);
        this.messages = null;
        this.code = code;
    }

    public BaseException(final int code, final List<String> messages) {
        super(String.join(", ", messages));
        this.messages = messages;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public List<String> getMessages() {
        return messages;
    }
}
