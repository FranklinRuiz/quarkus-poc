package pe.poc.account.domain.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pe.poc.account.domain.exception.InvalidAccountDataException;
import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;

/**
 * Test class for AccountDomainService.
 */
 class AccountDomainServiceTest {

    private AccountDomainService accountDomainService;

    @BeforeEach
     void setUp() {
        accountDomainService = new AccountDomainService();
    }

    @Test
     void testValidateAccount_ValidAccount_NoExceptionThrown() {
        // Arrange
        Account account = Account.builder()
                .id(AccountId.of(1L))
                .account("Test Account")
                .number("123456789")
                .status("ACTIVE")
                .build();

        // Act & Assert
        accountDomainService.validateAccount(account); // Should not throw exception
    }

    @Test
     void testValidateAccount_NullAccount_ThrowsException() {
        // Act & Assert
        assertThrows(InvalidAccountDataException.class, () -> {
            accountDomainService.validateAccount(null);
        });
    }

    @Test
     void testValidateAccount_EmptyAccountName_ThrowsException() {
        // Arrange
        Account account = Account.builder()
                .id(AccountId.of(1L))
                .account("")
                .number("123456789")
                .status("ACTIVE")
                .build();

        // Act & Assert
        assertThrows(InvalidAccountDataException.class, () -> {
            accountDomainService.validateAccount(account);
        });
    }

    @Test
     void testValidateAccount_EmptyAccountNumber_ThrowsException() {
        // Arrange
        Account account = Account.builder()
                .id(AccountId.of(1L))
                .account("Test Account")
                .number("")
                .status("ACTIVE")
                .build();

        // Act & Assert
        assertThrows(InvalidAccountDataException.class, () -> {
            accountDomainService.validateAccount(account);
        });
    }

    @Test
     void testValidateAccount_EmptyStatus_ThrowsException() {
        // Arrange
        Account account = Account.builder()
                .id(AccountId.of(1L))
                .account("Test Account")
                .number("123456789")
                .status("")
                .build();

        // Act & Assert
        assertThrows(InvalidAccountDataException.class, () -> {
            accountDomainService.validateAccount(account);
        });
    }

    @Test
     void testIsValidStatus_ValidStatuses_ReturnsTrue() {
        // Act & Assert
        assertTrue(accountDomainService.isValidStatus("ACTIVE"));
        assertTrue(accountDomainService.isValidStatus("INACTIVE"));
        assertTrue(accountDomainService.isValidStatus("SUSPENDED"));
    }

    @Test
     void testIsValidStatus_InvalidStatus_ReturnsFalse() {
        // Act & Assert
        assertFalse(accountDomainService.isValidStatus("UNKNOWN"));
        assertFalse(accountDomainService.isValidStatus(""));
        assertFalse(accountDomainService.isValidStatus(null));
    }
}