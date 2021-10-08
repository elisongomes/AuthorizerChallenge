package esg.secret.authorizerchallenge.infraestructure.delivery.services;

import esg.secret.authorizerchallenge.infraestructure.delivery.dto.AccountDTO;
import esg.secret.authorizerchallenge.infraestructure.delivery.dto.AllowListDTO;
import esg.secret.authorizerchallenge.infraestructure.delivery.dto.TransactionDTO;
import esg.secret.authorizerchallenge.infraestructure.delivery.rest.AccountRest;
import esg.secret.authorizerchallenge.infraestructure.delivery.rest.OperationRest;

import java.util.List;

public interface OperationService {
    OperationRest account(AccountDTO account);

    OperationRest transaction(TransactionDTO transaction);

    List<String> getViolations();

    OperationRest allowList(AllowListDTO allowListDTO);
}
