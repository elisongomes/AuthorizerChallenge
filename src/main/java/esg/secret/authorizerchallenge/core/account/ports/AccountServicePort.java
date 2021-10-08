package esg.secret.authorizerchallenge.core.account.ports;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public interface AccountServicePort {
    Account createAccount(Account account) throws ViolationException;

    Account findAccount();

    boolean accountHasBeenInitialized();

    boolean accountIsActive();

    Account updateAllowList(boolean allowedList);
}
