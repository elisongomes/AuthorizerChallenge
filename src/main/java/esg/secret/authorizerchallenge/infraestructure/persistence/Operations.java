package esg.secret.authorizerchallenge.infraestructure.persistence;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.infraestructure.persistence.entities.OperationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Operations {

    private static Operations instance = null;

    private final List<OperationEntity> operationsEntity;

    private Operations() {
        operationsEntity = new ArrayList<>();
    }

    public static Operations getInstance() {
        if (instance == null) {
            instance = new Operations();
        }
        return instance;
    }

    public Account getAccount() {
        return operationsEntity.size() == 0
            ? null
            : operationsEntity.get(operationsEntity.size() - 1).getAccount();
    }

    public void addOperation(OperationEntity operationEntity) {
        operationsEntity.add(operationEntity);
    }

    public List<OperationEntity> getOperations() {
        return operationsEntity;
    }

    public List<Transaction> getTransactionsWithFilter(Predicate<OperationEntity> predicate) {
        List<Transaction> transactions = new ArrayList<>();

        Operations.getInstance().getOperations().stream()
            .filter(predicate)
            .forEach(o -> transactions.add(o.getTransaction()));

        return transactions;
    }
}
