package esg.secret.authorizerchallenge.core.transaction.exception;

import esg.secret.authorizerchallenge.infraestructure.shared.exceptions.ViolationException;

public class HighFrequencySmallIntervalException extends ViolationException {
    public HighFrequencySmallIntervalException() {
        super(500, "high-frequency-small-interval");
    }
}
