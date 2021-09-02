package esg.secret.authorizerchallenge;

import esg.secret.authorizerchallenge.infraestructure.delivery.responses.ParserResponse;
import esg.secret.authorizerchallenge.infraestructure.delivery.services.impl.AuthorizerServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AuthorizerServiceTest {

    private static AuthorizerServiceImpl authorizerService;

    @BeforeAll
    static void setUp() {
        authorizerService = new AuthorizerServiceImpl();
    }

    @Test
    void test01_TransactionWithAccountNotInitialized() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2021-08-20T15:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("account-not-initialized"));
    }

    @Test
    void test02_AccountInitialize() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }

    @Test
    void test03_AccountInitializeAlreadyInitialized() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("account-already-initialized"));
    }

    @Test
    void test10_TransactionViolationInsufficientLimit() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 200, \"time\": \"2021-08-21T15:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("insufficient-limit"));
    }
}
