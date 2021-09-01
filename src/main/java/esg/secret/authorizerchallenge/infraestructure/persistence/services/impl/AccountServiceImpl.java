package esg.secret.authorizerchallenge.infraestructure.persistence.services.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.account.exception.AccountAlreadyInitializedException;
import esg.secret.authorizerchallenge.core.account.ports.AccountServicePort;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl.AccountRepositoryImpl;

public class AccountServiceImpl implements AccountServicePort {
    private final AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();

    @Override
    public Account createAccount(Account account) throws AccountAlreadyInitializedException {
        return accountRepository.createAccount(account);
    }

    @Override
    public Account findAccount() {
        return accountRepository.findAccount();
    }

    @Override
    public boolean accountHasBeenInitialized() {
        return accountRepository.accountHasBeenInitialized();
    }

    @Override
    public boolean accountIsActive() {
        return accountRepository.accountIsActive();
    }
}
