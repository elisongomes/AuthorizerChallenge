package esg.secret.authorizerchallenge.infraestructure.persistence.services.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.ports.TransactionServicePort;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;
import esg.secret.authorizerchallenge.infraestructure.persistence.violations.impl.*;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

import java.util.ArrayList;
import java.util.List;

public class TransactionServiceImpl implements TransactionServicePort {
    List<String> violations = new ArrayList<>();

    @Override
    public Transaction createTransaction(Transaction transaction) throws ViolationException {
        (new AccountRepositoryImpl()).debit(transaction);
        return transaction;
    }

    @Override
    public boolean isValid(Transaction transaction) {
        TransactionViolationServiceImpl transactionViolationService = (new TransactionViolationServiceImpl());
        transactionViolationService
            .add(new AccountNotInitializedViolation())
            .add(new CardNotActiveViolation())
            .add(new InsufficientLimitViolation())
            .add(new HighFrequencySmallIntervalViolation())
            .add(new DoubleTransactionViolation())
            .validate(transaction);

        violations = transactionViolationService.getViolations();

        return violations.isEmpty();
    }

    @Override
    public List<String> getViolations() {
        return violations;
    }
}
