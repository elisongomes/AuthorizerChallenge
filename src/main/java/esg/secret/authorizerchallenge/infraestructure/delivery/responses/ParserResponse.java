package esg.secret.authorizerchallenge.infraestructure.delivery.responses;

import java.util.ArrayList;
import java.util.List;

public class ParserResponse {
    boolean success;
    String message;
    List<String> violations;

    public ParserResponse(boolean success, String message, List<String> violations) {
        this.success = success;
        this.message = message;
        this.violations = violations;
    }

    public ParserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ParserResponse(boolean success, List<String> violations) {
        this.success = success;
        this.violations = violations;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getViolations() {
        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }
}
