package esg.secret.authorizerchallenge.core.transaction.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

import java.util.List;

public class TransactionViolationsException extends ViolationException {
    public TransactionViolationsException(List<String> messages) {
        super(500, messages);
    }
}
