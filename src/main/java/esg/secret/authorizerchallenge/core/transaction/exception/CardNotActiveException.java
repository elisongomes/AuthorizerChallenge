package esg.secret.authorizerchallenge.core.transaction.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class CardNotActiveException extends ViolationException {
    public CardNotActiveException() {
        super(500, "card-not-active");
    }
}
