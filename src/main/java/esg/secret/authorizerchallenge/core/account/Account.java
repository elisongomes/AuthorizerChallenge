package esg.secret.authorizerchallenge.core.account;

import java.io.Serializable;

public class Account implements Serializable {
    private boolean activeCard;
    private int availableLimit;
    private boolean allowListed;

    public Account(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.allowListed = false;
    }

    public Account(boolean activeCard, int availableLimit, boolean allowListed) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.allowListed = allowListed;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }

    public int getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(int availableLimit) {
        this.availableLimit = availableLimit;
    }

    public boolean isAllowListed() {
        return allowListed;
    }

    public void setAllowListed(boolean allowListed) {
        this.allowListed = allowListed;
    }
}
