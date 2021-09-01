package esg.secret.authorizerchallenge.core.transaction.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.exception.DoubleTransactionException;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public abstract class AbstractDoubleTransactionViolation extends AbstractViolation {
    public ViolationException fail() throws ViolationException {
        throw new DoubleTransactionException();
    }
}
