package esg.secret.authorizerchallenge.infraestructure.persistence.services.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.violations.TransactionViolation;
import esg.secret.authorizerchallenge.infraestructure.persistence.services.TransactionViolationService;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

import java.util.ArrayList;
import java.util.List;

public class TransactionViolationServiceImpl implements TransactionViolationService {
    private final List<String> violations = new ArrayList<>();
    private final List<TransactionViolation> transactionViolations = new ArrayList<>();

    @Override
    public TransactionViolationService add(TransactionViolation transactionViolation) {
        transactionViolations.add(transactionViolation);
        return this;
    }

    @Override
    public List<String> getViolations() {
        return this.violations;
    }

    @Override
    public boolean validate(Transaction transaction) {
        violations.clear();
        for (TransactionViolation transactionViolation : transactionViolations) {
            try {
                transactionViolation.validate(transaction);
            } catch (ViolationException ex) {
                violations.add(ex.getMessage());
                if (transactionViolation.stopOnFails()) {
                    break;
                }
            }
        }
        return violations.isEmpty();
    }
}
