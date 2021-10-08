package esg.secret.authorizerchallenge.core.transaction.violations;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public interface TransactionViolation {
    boolean validate(Transaction transaction) throws ViolationException;

    ViolationException fail() throws ViolationException;

    boolean stopOnFails();

    boolean validateOnlyAllowListed();
}
