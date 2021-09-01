package esg.secret.authorizerchallenge.core.transaction.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class DoubleTransactionException extends ViolationException {
    public DoubleTransactionException() {
        super(500, "double-transaction");
    }
}
