package esg.secret.authorizerchallenge.core.transaction;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private String merchant;
    private int amount;
    private Date datetime;

    public Transaction(String merchant, int amount, Date datetime) {
        this.merchant = merchant;
        this.amount = amount;
        this.datetime = datetime;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date datetime) {
        this.datetime = datetime;
    }
}
