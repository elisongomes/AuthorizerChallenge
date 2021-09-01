package esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.impl.AbstractCardNotActiveViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class CardNotActiveViolation extends AbstractCardNotActiveViolation {
    public CardNotActiveViolation() {
        this.stopOnFails = true;
    }
    @Override
    public boolean validate(Transaction transaction) throws ViolationException {
        if (!(new AccountRepositoryImpl()).accountIsActive()) {
            this.fail();
        }
        return true;
    }

    @Override
    public boolean stopOnFails() {
        return this.stopOnFails;
    }
}
