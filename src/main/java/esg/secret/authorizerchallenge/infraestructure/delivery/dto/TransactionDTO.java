package esg.secret.authorizerchallenge.infraestructure.delivery.dto;

import java.util.Date;

public class TransactionDTO {
    private String merchant;
    private int amount;
    private Date datetime;

    public TransactionDTO(String merchant, int amount, Date datetime) {
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
