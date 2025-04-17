package pe.poc.account.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.poc.account.application.dto.AccountRequestDTO;
import pe.poc.account.application.dto.AccountResponseDTO;
import pe.poc.account.application.port.output.IAccountRepository;
import pe.poc.account.domain.exception.AccountNotFoundException;
import pe.poc.account.domain.exception.InvalidAccountDataException;
import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;
import pe.poc.account.domain.service.AccountDomainService;

/**
 * Test class for AccountManagementService.
 */
@ExtendWith(MockitoExtension.class)
 class AccountManagementServiceTest {

    @Mock
    private IAccountRepository accountRepository;

    @Mock
    private AccountDomainService accountDomainService;

    private AccountManagementService accountManagementService;

    @BeforeEach
     void setUp() {
        accountManagementService = new AccountManagementService(accountRepository, accountDomainService);
    }

    @Test
     void testCreateAccount_ValidData_ReturnsCreatedAccount() {
        // Arrange
        AccountRequestDTO requestDTO = new AccountRequestDTO("Test Account", "123456789", "ACTIVE");
        
        Account savedAccount = Account.builder()
                .id(AccountId.of(1L))
                .account(requestDTO.getAccount())
                .number(requestDTO.getNumber())
                .status(requestDTO.getStatus())
                .build();
        
        Mockito.doNothing().when(accountDomainService).validateAccount(ArgumentMatchers.any(Account.class));
        Mockito.when(accountDomainService.isValidStatus(requestDTO.getStatus())).thenReturn(true);
        Mockito.when(accountRepository.save(ArgumentMatchers.any(Account.class))).thenReturn(savedAccount);

        // Act
        AccountResponseDTO responseDTO = accountManagementService.createAccount(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals(requestDTO.getAccount(), responseDTO.getAccount());
        assertEquals(requestDTO.getNumber(), responseDTO.getNumber());
        assertEquals(requestDTO.getStatus(), responseDTO.getStatus());
        
        Mockito.verify(accountDomainService, Mockito.times(1)).validateAccount(ArgumentMatchers.any(Account.class));
        Mockito.verify(accountDomainService, Mockito.times(1)).isValidStatus(requestDTO.getStatus());
        Mockito.verify(accountRepository, Mockito.times(1)).save(ArgumentMatchers.any(Account.class));
    }

    @Test
     void testCreateAccount_InvalidStatus_ThrowsException() {
        // Arrange
        AccountRequestDTO requestDTO = new AccountRequestDTO("Test Account", "123456789", "INVALID_STATUS");
        
        Mockito.doNothing().when(accountDomainService).validateAccount(ArgumentMatchers.any(Account.class));
        Mockito.when(accountDomainService.isValidStatus(requestDTO.getStatus())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidAccountDataException.class, () -> {
            accountManagementService.createAccount(requestDTO);
        });
        
        Mockito.verify(accountDomainService, Mockito.times(1)).validateAccount(ArgumentMatchers.any(Account.class));
        Mockito.verify(accountDomainService, Mockito.times(1)).isValidStatus(requestDTO.getStatus());
        Mockito.verify(accountRepository, Mockito.times(0)).save(ArgumentMatchers.any(Account.class));
    }

    @Test
     void testGetAccountById_ExistingId_ReturnsAccount() {
        // Arrange
        Long accountId = 1L;
        Account account = Account.builder()
                .id(AccountId.of(accountId))
                .account("Test Account")
                .number("123456789")
                .status("ACTIVE")
                .build();
        
        Mockito.when(accountRepository.findById(AccountId.of(accountId))).thenReturn(Optional.of(account));

        // Act
        AccountResponseDTO responseDTO = accountManagementService.getAccountById(accountId);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(accountId, responseDTO.getId());
        assertEquals(account.getAccount(), responseDTO.getAccount());
        assertEquals(account.getNumber(), responseDTO.getNumber());
        assertEquals(account.getStatus(), responseDTO.getStatus());
        
        Mockito.verify(accountRepository, Mockito.times(1)).findById(AccountId.of(accountId));
    }

    @Test
     void testGetAccountById_NonExistingId_ThrowsException() {
        // Arrange
        Long accountId = 999L;
        Mockito.when(accountRepository.findById(AccountId.of(accountId))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            accountManagementService.getAccountById(accountId);
        });
        
        Mockito.verify(accountRepository, Mockito.times(1)).findById(AccountId.of(accountId));
    }

    @Test
     void testGetAllAccounts_ReturnsAllAccounts() {
        // Arrange
        Account account1 = Account.builder()
                .id(AccountId.of(1L))
                .account("Account 1")
                .number("123456789")
                .status("ACTIVE")
                .build();
        
        Account account2 = Account.builder()
                .id(AccountId.of(2L))
                .account("Account 2")
                .number("987654321")
                .status("INACTIVE")
                .build();
        
        List<Account> accounts = Arrays.asList(account1, account2);
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        // Act
        List<AccountResponseDTO> responseDTOs = accountManagementService.getAllAccounts();

        // Assert
        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
        assertEquals(account1.getId().getValue(), responseDTOs.get(0).getId());
        assertEquals(account1.getAccount(), responseDTOs.get(0).getAccount());
        assertEquals(account2.getId().getValue(), responseDTOs.get(1).getId());
        assertEquals(account2.getAccount(), responseDTOs.get(1).getAccount());
        
        Mockito.verify(accountRepository, Mockito.times(1)).findAll();
    }
}