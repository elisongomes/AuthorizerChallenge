package esg.secret.authorizerchallenge.infraestructure.persistence.entities;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.transaction.Transaction;

public class OperationEntity {
    private Account account;
    private Transaction transaction;

    public OperationEntity(Account account) {
        this.account = account;
        this.transaction = null;
    }

    public OperationEntity(Account account, Transaction transaction) {
        this.account = account;
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
