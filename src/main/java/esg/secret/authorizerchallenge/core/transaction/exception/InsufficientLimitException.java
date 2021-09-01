package esg.secret.authorizerchallenge.core.transaction.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class InsufficientLimitException extends ViolationException {
    public InsufficientLimitException() {
        super(500, "insufficient-limit");
    }
}
