package esg.secret.authorizerchallenge.infraestructure.persistence.services;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.TransactionViolation;

import java.util.List;

public interface TransactionViolationService {
    TransactionViolationService add(TransactionViolation transactionViolation);

    List<String> getViolations();

    boolean validate(Transaction transaction);
}
