package esg.secret.authorizerchallenge.infraestructure.delivery.services.impl;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.core.account.usecase.impl.CreateAccountUseCaseImpl;
import esg.secret.authorizerchallenge.core.account.usecase.impl.FindAccountUseCaseImpl;
import esg.secret.authorizerchallenge.core.transaction.Transaction;
import esg.secret.authorizerchallenge.core.transaction.usecase.impl.CreateTransactionUseCaseImpl;
import esg.secret.authorizerchallenge.infraestructure.delivery.converters.AccountRestConverter;
import esg.secret.authorizerchallenge.infraestructure.delivery.dto.AccountDTO;
import esg.secret.authorizerchallenge.infraestructure.delivery.dto.TransactionDTO;
import esg.secret.authorizerchallenge.infraestructure.delivery.rest.OperationRest;
import esg.secret.authorizerchallenge.infraestructure.delivery.services.OperationService;
import esg.secret.authorizerchallenge.infraestructure.persistence.services.impl.AccountServiceImpl;
import esg.secret.authorizerchallenge.infraestructure.persistence.services.impl.TransactionServiceImpl;
import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

import java.util.ArrayList;
import java.util.List;

public class OperationServiceImpl implements OperationService {

    private final AccountRestConverter accountRestConverter = new AccountRestConverter();

    private final CreateAccountUseCaseImpl createAccountUseCase = new CreateAccountUseCaseImpl(
        new AccountServiceImpl()
    );

    private final FindAccountUseCaseImpl findAccountUseCase = new FindAccountUseCaseImpl(
        new AccountServiceImpl()
    );

    private final CreateTransactionUseCaseImpl createTransactionUseCase = new CreateTransactionUseCaseImpl(
        new TransactionServiceImpl()
    );

    private final List<String> violations = new ArrayList<>();

    @Override
    public OperationRest account(AccountDTO accountDTO) {
        violations.clear();
        OperationRest operationRest = new OperationRest();
        Account account;
        try {
            account = createAccountUseCase.execute(
                new Account(
                    accountDTO.isActiveCard(),
                    accountDTO.getAvailableLimit()
                )
            );
        } catch (ViolationException ex) {
            operationRest.addViolation(ex.getMessage());
            account = findAccountUseCase.execute();
        }
        operationRest.setAccount(
            accountRestConverter.mapToRest(account)
        );

        return operationRest;
    }

    @Override
    public OperationRest transaction(TransactionDTO transaction) {
        violations.clear();
        OperationRest operationRest = new OperationRest();
        try {
            createTransactionUseCase.execute(
                new Transaction(
                    transaction.getMerchant(),
                    transaction.getAmount(),
                    transaction.getDateTime()
                )
            );
        } catch (ViolationException ex) {
            operationRest.setViolations(ex.getMessages());
            violations.addAll(ex.getMessages());
        }
        operationRest.setAccount(
            accountRestConverter.mapToRest(findAccountUseCase.execute())
        );
        return operationRest;
    }

    @Override
    public List<String> getViolations() {
        return violations;
    }


}
