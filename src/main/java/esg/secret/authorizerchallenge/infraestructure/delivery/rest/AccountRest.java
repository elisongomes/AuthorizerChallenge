package esg.secret.authorizerchallenge.infraestructure.delivery.rest;

import java.io.Serializable;

public class AccountRest implements Serializable {
    private boolean activeCard;
    private int availableLimit;

    public AccountRest(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
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
}
