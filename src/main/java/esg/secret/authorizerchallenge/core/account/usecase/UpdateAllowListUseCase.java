package esg.secret.authorizerchallenge.core.account.usecase;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.BaseException;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public interface UpdateAllowListUseCase {
    Account execute(boolean allowListed) throws ViolationException;
}
