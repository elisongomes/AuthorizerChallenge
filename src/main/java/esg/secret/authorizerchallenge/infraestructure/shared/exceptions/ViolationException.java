package esg.secret.authorizerchallenge.infraestructure.shared.exceptions;

import java.util.List;

public class ViolationException extends BaseException {
    public ViolationException(int code, String message) {
        super(code, message);
    }

    public ViolationException(int code, List<String> messages) {
        super(code, messages);
    }
}
