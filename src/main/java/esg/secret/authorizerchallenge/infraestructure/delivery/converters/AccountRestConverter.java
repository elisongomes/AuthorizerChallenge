package esg.secret.authorizerchallenge.infraestructure.delivery.converters;

import esg.secret.authorizerchallenge.core.account.Account;
import esg.secret.authorizerchallenge.infraestructure.delivery.rest.AccountRest;
import esg.secret.authorizerchallenge.infraestructure.shared.RestConverter;

public class AccountRestConverter implements RestConverter<AccountRest, Account> {
    @Override
    public Account mapToEntity(final AccountRest rest) {
        return new Account(rest.isActiveCard(), rest.getAvailableLimit());
    }

    @Override
    public AccountRest mapToRest(final Account entity) {
        if (entity == null) {
            return null;
        }
        return new AccountRest(entity.isActiveCard(), entity.getAvailableLimit());
    }
}
