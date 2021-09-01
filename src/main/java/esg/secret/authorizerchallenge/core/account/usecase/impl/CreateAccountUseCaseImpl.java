package esg.secret.authorizerchallenge.core.account.usecase.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.account.exception.AccountAlreadyInitializedException;
import esg.secret.authorizerchallenge.core.account.ports.AccountServicePort;
import esg.secret.authorizerchallenge.core.account.usecase.CreateAccountUseCase;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountServicePort accountServicePort;

    public CreateAccountUseCaseImpl(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    @Override
    public Account execute(Account account) throws ViolationException {
        if (this.accountServicePort.accountHasBeenInitialized()) {
            throw new AccountAlreadyInitializedException();
        }
        return this.accountServicePort.createAccount(account);
    }
}
