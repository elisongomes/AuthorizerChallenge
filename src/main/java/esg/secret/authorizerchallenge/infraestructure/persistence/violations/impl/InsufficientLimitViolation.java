package esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.impl.AbstractInsufficientLimitViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class InsufficientLimitViolation extends AbstractInsufficientLimitViolation {

    @Override
    public boolean validate(Transaction transaction) throws ViolationException {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        Account account = accountRepository.findAccount();
        int newAvailableLimit = account.getAvailableLimit() - transaction.getAmount();
        if (newAvailableLimit < 0) {
            fail();
        }
        return true;
    }

    @Override
    public boolean stopOnFails() {
        return stopOnFails;
    }
}
