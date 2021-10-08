package esg.secret.authorizerchallenge.core.account.usecase.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.account.exception.AccountNotInitializedException;
import esg.secret.authorizerchallenge.core.account.ports.AccountServicePort;
import esg.secret.authorizerchallenge.core.account.usecase.UpdateAllowListUseCase;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class UpdateAllowListUseCaseImpl implements UpdateAllowListUseCase {

    private final AccountServicePort accountServicePort;

    public UpdateAllowListUseCaseImpl(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    @Override
    public Account execute(boolean allowedList) throws ViolationException {
        if (!accountServicePort.accountHasBeenInitialized()) {
            throw new AccountNotInitializedException();
        }
        return accountServicePort.updateAllowList(allowedList);
    }
}
