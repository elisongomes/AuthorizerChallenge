package esg.secret.authorizerchallenge.core.transaction.ports;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

import java.util.List;

public interface TransactionServicePort {
    Transaction createTransaction(Transaction transaction) throws ViolationException;

    boolean isValid(Transaction transaction);

    List<String> getViolations();
}
