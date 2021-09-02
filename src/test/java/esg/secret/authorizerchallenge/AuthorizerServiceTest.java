package esg.secret.authorizerchallenge;

import esg.secret.authorizerchallenge.infraestructure.delivery.responses.ParserResponse;
import esg.secret.authorizerchallenge.infraestructure.delivery.services.impl.AuthorizerServiceImpl;
import esg.secret.authorizerchallenge.infraestructure.persistence.Operations;
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
    void test00_UnknownOperation() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"withdraw\": {\"amount\": 20, \"time\": \"2021-08-20T15:30:00.000Z\"}}"
        );
        assertFalse(parserResponse.isSuccess());
        assertEquals("Unknown operation", parserResponse.getMessage());
        assertNull(parserResponse.getViolations());
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
    void test02_AccountInitializeInactiveCard() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": false, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }

    @Test
    void test03_TransactionWithAccountInactiveCard() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2021-08-20T15:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("card-not-active"));
    }

    @Test
    void test10_AccountInitialize() {
        Operations.getInstance().clearOperations();

        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }

    @Test
    void test11_AccountInitializeAlreadyInitialized() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("account-already-initialized"));
    }

    @Test
    void test20_TransactionViolationInsufficientLimit() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 200, \"time\": \"2021-08-21T15:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("insufficient-limit"));
    }

    @Test
    void test21_TransactionHighFrequencySmallInterval01() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 10, \"time\": \"2021-08-21T15:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval02() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 20, \"time\": \"2021-08-21T15:30:10.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval03() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 30, \"time\": \"2021-08-21T15:31:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval04Violation() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 40, \"time\": \"2021-08-21T15:32:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("high-frequency-small-interval"));
    }
    @Test
    void test22_TransactionHighFrequencySmallIntervalAndDoubleTransaction() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 20, \"time\": \"2021-08-21T15:32:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(2, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("high-frequency-small-interval"));
        assertTrue(parserResponse.getViolations().contains("double-transaction"));
    }
}
