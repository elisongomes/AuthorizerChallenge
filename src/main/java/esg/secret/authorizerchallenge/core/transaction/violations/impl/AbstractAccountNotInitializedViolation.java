package esg.secret.authorizerchallenge.core.transaction.violations.impl;

import esg.secret.authorizerchallenge.core.account.exception.AccountNotInitializedException;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public abstract class AbstractAccountNotInitializedViolation extends AbstractViolation {
    @Override
    public ViolationException fail() throws ViolationException {
        throw new AccountNotInitializedException();
    }
}
