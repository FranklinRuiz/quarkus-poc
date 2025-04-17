package pe.poc.account.infrastructure.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import io.quarkus.test.InjectMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response.Status;
import pe.poc.account.application.dto.AccountRequestDTO;
import pe.poc.account.application.dto.AccountResponseDTO;
import pe.poc.account.application.port.input.IAccountManagementUseCase;
import pe.poc.account.domain.exception.AccountNotFoundException;

/**
 * Test class for AccountController.
 */
@QuarkusTest
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @InjectMock
    private IAccountManagementUseCase accountManagementUseCase;

    private AccountRequestDTO validAccountRequest;
    private AccountResponseDTO accountResponse1;
    private AccountResponseDTO accountResponse2;

    @BeforeEach
    void setUp() {
        // Setup test data
        validAccountRequest = new AccountRequestDTO("Test Account", "123456789", "ACTIVE");

        accountResponse1 = new AccountResponseDTO(1L, "Test Account", "123456789", "ACTIVE");
        accountResponse2 = new AccountResponseDTO(2L, "Another Account", "987654321", "INACTIVE");
    }

    @Test
     void testCreateAccount_ValidData_ReturnsCreatedAccount() {
        // Arrange
        when(accountManagementUseCase.createAccount(any(AccountRequestDTO.class))).thenReturn(accountResponse1);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(validAccountRequest)
                .when()
                .post("/api/accounts")
                .then()
                .statusCode(Status.CREATED.getStatusCode())
                .body("id", is(accountResponse1.getId().intValue()))
                .body("account", equalTo(accountResponse1.getAccount()))
                .body("number", equalTo(accountResponse1.getNumber()))
                .body("status", equalTo(accountResponse1.getStatus()));
    }

    @Test
     void testGetAccountById_ExistingId_ReturnsAccount() {
        // Arrange
        Long accountId = 1L;
        when(accountManagementUseCase.getAccountById(accountId)).thenReturn(accountResponse1);

        // Act & Assert
        given()
                .when()
                .get("/api/accounts/{id}", accountId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("id", is(accountResponse1.getId().intValue()))
                .body("account", equalTo(accountResponse1.getAccount()))
                .body("number", equalTo(accountResponse1.getNumber()))
                .body("status", equalTo(accountResponse1.getStatus()));
    }

    @Test
     void testGetAccountById_NonExistingId_ReturnsNotFound() {
        // Arrange
        Long accountId = 999L;
        when(accountManagementUseCase.getAccountById(accountId))
                .thenThrow(new AccountNotFoundException(accountId));

        // Act & Assert
        given()
                .when()
                .get("/api/accounts/{id}", accountId)
                .then()
                .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
     void testGetAllAccounts_ReturnsAllAccounts() {
        // Arrange
        List<AccountResponseDTO> accounts = Arrays.asList(accountResponse1, accountResponse2);
        when(accountManagementUseCase.getAllAccounts()).thenReturn(accounts);

        // Act & Assert
        given()
                .when()
                .get("/api/accounts")
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("size()", is(2))
                .body("[0].id", is(accountResponse1.getId().intValue()))
                .body("[0].account", equalTo(accountResponse1.getAccount()))
                .body("[1].id", is(accountResponse2.getId().intValue()))
                .body("[1].account", equalTo(accountResponse2.getAccount()));
    }

    @Test
     void testUpdateAccount_ValidData_ReturnsUpdatedAccount() {
        // Arrange
        Long accountId = 1L;
        when(accountManagementUseCase.updateAccount(eq(accountId), any(AccountRequestDTO.class)))
                .thenReturn(accountResponse1);

        // Act & Assert
        given()
                .contentType(ContentType.JSON)
                .body(validAccountRequest)
                .when()
                .put("/api/accounts/{id}", accountId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("id", is(accountResponse1.getId().intValue()))
                .body("account", equalTo(accountResponse1.getAccount()))
                .body("number", equalTo(accountResponse1.getNumber()))
                .body("status", equalTo(accountResponse1.getStatus()));
    }

    @Test
     void testDeleteAccount_ExistingId_ReturnsNoContent() {
        // Arrange
        Long accountId = 1L;
        doNothing().when(accountManagementUseCase).deleteAccount(accountId);

        // Act & Assert
        given()
                .when()
                .delete("/api/accounts/{id}", accountId)
                .then()
                .statusCode(Status.NO_CONTENT.getStatusCode());
    }

    @Test
     void testActivateAccount_ExistingId_ReturnsActivatedAccount() {
        // Arrange
        Long accountId = 1L;
        AccountResponseDTO activatedAccount = new AccountResponseDTO(
                accountResponse1.getId(),
                accountResponse1.getAccount(),
                accountResponse1.getNumber(),
                "ACTIVE");

        when(accountManagementUseCase.activateAccount(accountId)).thenReturn(activatedAccount);

        // Act & Assert
        given()
                .when()
                .put("/api/accounts/{id}/activate", accountId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("id", is(activatedAccount.getId().intValue()))
                .body("status", equalTo("ACTIVE"));
    }

    @Test
     void testDeactivateAccount_ExistingId_ReturnsDeactivatedAccount() {
        // Arrange
        Long accountId = 1L;
        AccountResponseDTO deactivatedAccount = new AccountResponseDTO(
                accountResponse1.getId(),
                accountResponse1.getAccount(),
                accountResponse1.getNumber(),
                "INACTIVE");

        when(accountManagementUseCase.deactivateAccount(accountId)).thenReturn(deactivatedAccount);

        // Act & Assert
        given()
                .when()
                .put("/api/accounts/{id}/deactivate", accountId)
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("id", is(deactivatedAccount.getId().intValue()))
                .body("status", equalTo("INACTIVE"));
    }
}
