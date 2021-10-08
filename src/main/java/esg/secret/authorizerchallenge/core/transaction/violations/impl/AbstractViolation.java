package esg.secret.authorizerchallenge.core.transaction.violations.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.TransactionViolation;

public abstract class AbstractViolation implements TransactionViolation {
    public boolean stopOnFails = false;

    public boolean validateOnlyAllowListed() {
        return false;
    }
}
