package esg.secret.authorizerchallenge.infraestructure.delivery.dto;

public class AllowListDTO {
    private boolean active;

    public AllowListDTO(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
