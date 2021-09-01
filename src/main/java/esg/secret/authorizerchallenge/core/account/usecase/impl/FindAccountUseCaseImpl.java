package esg.secret.authorizerchallenge.core.account.usecase.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.account.ports.AccountServicePort;
import esg.secret.authorizerchallenge.core.account.usecase.FindAccountUseCase;

public class FindAccountUseCaseImpl implements FindAccountUseCase {

    private final AccountServicePort accountServicePort;

    public FindAccountUseCaseImpl(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    @Override
    public Account execute() {
        return accountServicePort.findAccount();
    }
}
