package esg.secret.authorizerchallenge.core.account.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class AccountNotInitializedException extends ViolationException {
    public AccountNotInitializedException() {
        super(500, "account-not-initialized");
    }
}
