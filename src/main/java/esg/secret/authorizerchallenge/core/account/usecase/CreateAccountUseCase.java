package esg.secret.authorizerchallenge.core.account.usecase;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.BaseException;

public interface CreateAccountUseCase {
    Account execute(Account account) throws BaseException;
}
