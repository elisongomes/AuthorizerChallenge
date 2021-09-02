package esg.secret.authorizerchallenge.infraestructure.delivery.responses;

import esg.secret.authorizerchallenge.infraestructure.delivery.rest.OperationRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public class JsonResponse {
    public static String stdout(OperationRest operationRest) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();

        ObjectNode accountNode = mapper.createObjectNode();
        if (operationRest.getAccount() != null) {
            accountNode.put("active-card", operationRest.getAccount().isActiveCard());
            accountNode.put("available-limit", operationRest.getAccount().getAvailableLimit());
        }
        rootNode.set("account", accountNode);

        List<String> violations = new ArrayList<>();
        if (operationRest.getViolations() != null) {
            violations.addAll(operationRest.getViolations());
        }
        rootNode.set("violations", mapper.valueToTree(violations));

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonString);
        return jsonString;
    }
}
