package esg.secret.authorizerchallenge.infraestructure.persistence.repositories;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.transaction.Transaction;

import java.util.List;

public interface AccountRepository {
    Account createAccount(Account account);

    Account findAccount();

    boolean accountHasBeenInitialized();

    boolean accountIsActive();

    void addOperation(Account account, Transaction transaction);

    void debit(Transaction transaction);

    List<Transaction> getTransactionsByInterval(long time, int interval);

    List<Transaction> getTransactionsByMerchantAmountInterval(
        long time,
        int interval,
        String merchant,
        int amount
    );
}
