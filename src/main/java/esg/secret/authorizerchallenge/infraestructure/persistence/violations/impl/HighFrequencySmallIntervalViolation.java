package esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.impl.AbstractHighFrequencySmallIntervalViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class HighFrequencySmallIntervalViolation extends AbstractHighFrequencySmallIntervalViolation {
    private final int RATE_LIMIT_TRANSACTION_COUNT = 3;
    private final int RATE_LIMIT_TRANSACTION_MINUTE = 2;


    @Override
    public boolean validate(Transaction transaction) throws ViolationException {
        if ((new AccountRepositoryImpl()).findAccount().isAllowListed()) {
            return true;
        }

        if ((new AccountRepositoryImpl()).getTransactionsByInterval(
            transaction.getDateTime().getTime(),
            RATE_LIMIT_TRANSACTION_MINUTE
        ).size() >= RATE_LIMIT_TRANSACTION_COUNT) {
            fail();
        }
        return true;
    }

    @Override
    public boolean stopOnFails() {
        return stopOnFails;
    }

    @Override
    public boolean validateOnlyAllowListed() {
        return true;
    }
}
