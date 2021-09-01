package esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.impl.AbstractAccountNotInitializedViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class AccountNotInitializedViolation extends AbstractAccountNotInitializedViolation {
    public AccountNotInitializedViolation() {
        stopOnFails = true;
    }

    @Override
    public boolean validate(Transaction transaction) throws ViolationException {
        if (!(new AccountRepositoryImpl()).accountHasBeenInitialized()) {
            fail();
        }
        return true;
    }

    @Override
    public boolean stopOnFails() {
        return stopOnFails;
    }
}
