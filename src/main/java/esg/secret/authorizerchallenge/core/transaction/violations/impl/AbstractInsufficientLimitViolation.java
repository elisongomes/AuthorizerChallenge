package esg.secret.authorizerchallenge.core.transaction.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.exception.InsufficientLimitException;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public abstract class AbstractInsufficientLimitViolation extends AbstractViolation {
    @Override
    public ViolationException fail() throws ViolationException {
        throw new InsufficientLimitException();
    }
}
