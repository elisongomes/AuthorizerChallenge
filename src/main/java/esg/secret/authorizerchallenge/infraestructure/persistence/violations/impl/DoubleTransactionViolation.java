package esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.impl.AbstractDoubleTransactionViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class DoubleTransactionViolation extends AbstractDoubleTransactionViolation {
    private final int RATE_LIMIT_TRANSACTION_COUNT = 1;
    private final int RATE_LIMIT_TRANSACTION_MINUTE = 2;

    @Override
    public boolean validate(Transaction transaction) throws ViolationException {
        if ((new AccountRepositoryImpl()).getTransactionsByMerchantAmountInterval(
            transaction.getDateTime().getTime(),
            RATE_LIMIT_TRANSACTION_MINUTE,
            transaction.getMerchant(),
            transaction.getAmount()
        ).size() >= RATE_LIMIT_TRANSACTION_COUNT) {
            this.fail();
        }
        return true;
    }

    @Override
    public boolean stopOnFails() {
        return this.stopOnFails;
    }
}
