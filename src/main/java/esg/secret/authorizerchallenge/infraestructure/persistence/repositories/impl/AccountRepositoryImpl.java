package esg.secret.authorizerchallenge.infraestructure.persistence.repositories.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.infraestructure.persistence.Operations;
import esg.secret.authorizerchallenge.infraestructure.persistence.entities.OperationEntity;
import esg.secret.authorizerchallenge.infraestructure.persistence.repositories.AccountRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account createAccount(Account account) {
        this.addOperation(account, null);
        return this.findAccount();
    }

    @Override
    public Account findAccount() {
        return Operations.getInstance().getAccount();
    }

    @Override
    public boolean accountHasBeenInitialized() {
        return this.findAccount() != null;
    }

    @Override
    public boolean accountIsActive() {
        if (!this.accountHasBeenInitialized()) {
            return false;
        }
        return this.findAccount().isActiveCard();
    }

    @Override
    public void addOperation(Account account, Transaction transaction) {
        Operations.getInstance().addOperation(
            new OperationEntity(
                account,
                transaction
            )
        );
    }

    @Override
    public void debit(Transaction transaction) {
        Account account = this.findAccount();
        int newAvailableLimit = this.findAccount().getAvailableLimit() - transaction.getAmount();
        if (newAvailableLimit >= 0) {
            account.setAvailableLimit(newAvailableLimit);
            this.addOperation(account, transaction);
        }
    }

    @Override
    public List<Transaction> getTransactionsByInterval(long time, int interval) {
        return Operations.getInstance().getTransactionsWithFilter(
            o -> (
                o.getTransaction() != null && TimeUnit.MILLISECONDS.toMinutes(
                    time - o.getTransaction().getDateTime().getTime()
                ) <= interval
            )
        );
    }

    @Override
    public List<Transaction> getTransactionsByMerchantAmountInterval(
        long time,
        int interval,
        String merchant,
        int amount
    ) {
        return Operations.getInstance().getTransactionsWithFilter(
            o -> (
                o.getTransaction() != null && TimeUnit.MILLISECONDS.toMinutes(
                    time - o.getTransaction().getDateTime().getTime()
                ) <= interval
                    && o.getTransaction().getMerchant().equalsIgnoreCase(merchant)
                    && o.getTransaction().getAmount() == amount
            )
        );
    }

}
