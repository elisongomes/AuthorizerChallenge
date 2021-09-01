package esg.secret.authorizerchallenge.core.account.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class AccountAlreadyInitializedException extends ViolationException {
    public AccountAlreadyInitializedException() {
        super(500, "account-already-initialized");
    }
}