package esg.secret.authorizerchallenge.infraestructure.delivery.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface AuthorizerService {
    boolean run();

    boolean parserLine(String operation);

    boolean processAccount(JsonNode objJson);

    boolean processTransaction(JsonNode objJson);
}
