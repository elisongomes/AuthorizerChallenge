package esg.secret.authorizerchallenge.infraestructure.delivery.rest;

import java.util.ArrayList;
import java.util.List;

public class OperationRest {
    AccountRest account;
    List<String> violations;

    public OperationRest() {

    }

    public OperationRest(AccountRest account) {
        this.account = account;
        this.violations = null;
    }

    public OperationRest(AccountRest account, List<String> violations) {
        this.account = account;
        this.violations = violations;
    }

    public AccountRest getAccount() {
        return account;
    }

    public void setAccount(AccountRest account) {
        this.account = account;
    }

    public List<String> getViolations() {
        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }

    public void addViolation(String violation) {
        if (this.violations == null) {
            this.violations = new ArrayList<>();
        }
        this.violations.add(violation);
    }
}
