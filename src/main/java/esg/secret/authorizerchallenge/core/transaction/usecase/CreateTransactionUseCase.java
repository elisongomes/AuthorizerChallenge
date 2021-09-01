package esg.secret.authorizerchallenge.core.transaction.usecase;

import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.BaseException;

public interface CreateTransactionUseCase {
    Transaction execute(Transaction transaction) throws BaseException;
}
