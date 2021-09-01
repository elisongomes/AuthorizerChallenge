package esg.secret.authorizerchallenge.core.transaction.usecase.impl;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.exception.TransactionViolationsException;
import esg.secret.authorizerchallenge.core.transaction.ports.TransactionServicePort;
import esg.secret.authorizerchallenge.core.transaction.usecase.CreateTransactionUseCase;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionServicePort transactionServicePort;

    public CreateTransactionUseCaseImpl(TransactionServicePort transactionServicePort) {
        this.transactionServicePort = transactionServicePort;
    }

    @Override
    public Transaction execute(Transaction transaction) throws ViolationException {

        if (!transactionServicePort.isValid(transaction)) {
            throw new TransactionViolationsException(transactionServicePort.getViolations());
        }

        return transactionServicePort.createTransaction(transaction);
    }
}
