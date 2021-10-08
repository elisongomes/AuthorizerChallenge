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
    void test01_AllowListWithAccountNotInitialized() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{ \"allow-list\": { \"active\": true } }"
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
    void test10_ClearAccountState() {
        Operations.getInstance().clearOperations();
        assertEquals(0, Operations.getInstance().getOperations().size());
    }

    @Test
    void test11_AccountInitialize() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"account\": {\"active-card\": true, \"available-limit\": 100}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }

    @Test
    void test12_AccountInitializeAlreadyInitialized() {
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
        assertEquals(90, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval02() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 20, \"time\": \"2021-08-21T15:30:10.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(70, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval03() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 30, \"time\": \"2021-08-21T15:31:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(40, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test21_TransactionHighFrequencySmallInterval04Violation() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 40, \"time\": \"2021-08-21T15:32:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertEquals(1, parserResponse.getViolations().size());
        assertTrue(parserResponse.getViolations().contains("high-frequency-small-interval"));
        assertEquals(40, Operations.getInstance().getAccount().getAvailableLimit());
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
        assertEquals(40, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test23_TransactionSuccess() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Bobs's\", \"amount\": 10, \"time\": \"2021-08-21T15:35:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(30, Operations.getInstance().getAccount().getAvailableLimit());
    }

    @Test
    void test30_UpdateAccountAllowList() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{ \"allow-list\": { \"active\": true } }"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }
    @Test
    void test31_TransactionHighFrequencySmallInterval01() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 1, \"time\": \"2021-10-07T12:30:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(29, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test31_TransactionHighFrequencySmallInterval02() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 1, \"time\": \"2021-10-07T12:30:10.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(28, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test31_TransactionHighFrequencySmallInterval03() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 1, \"time\": \"2021-10-07T12:31:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
        assertEquals(27, Operations.getInstance().getAccount().getAvailableLimit());
    }
    @Test
    void test31_TransactionHighFrequencySmallInterval04Violation() {
        ParserResponse parserResponse = authorizerService.parserLine(
            "{\"transaction\": {\"merchant\": \"Habbib's\", \"amount\": 1, \"time\": \"2021-10-07T12:32:00.000Z\"}}"
        );
        assertTrue(parserResponse.isSuccess());
        assertNull(parserResponse.getViolations());
    }
}
