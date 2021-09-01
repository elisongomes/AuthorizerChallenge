package esg.secret.authorizerchallenge;

import esg.secret.authorizerchallenge.infraestructure.delivery.services.impl.AuthorizerServiceImpl;

public class AuthorizerChallengeApplication {
    public static void main(String[] args) {
        (new AuthorizerServiceImpl()).run();
    }
}
