package esg.secret.authorizerchallenge.infraestructure.delivery.services;

import com.fasterxml.jackson.databind.JsonNode;
import esg.secret.authorizerchallenge.infraestructure.delivery.responses.ParserResponse;

public interface AuthorizerService {
    boolean run();

    ParserResponse parserLine(String operation);

    ParserResponse processAccount(JsonNode objJson);

    ParserResponse processTransaction(JsonNode objJson);

    ParserResponse processAllowList(JsonNode objJson);
}
