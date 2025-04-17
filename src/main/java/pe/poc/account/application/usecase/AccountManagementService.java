package pe.poc.account.application.usecase;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import pe.poc.account.application.dto.AccountRequestDTO;
import pe.poc.account.application.dto.AccountResponseDTO;
import pe.poc.account.application.port.input.IAccountManagementUseCase;
import pe.poc.account.application.port.output.IAccountRepository;
import pe.poc.account.domain.exception.AccountNotFoundException;
import pe.poc.account.domain.exception.InvalidAccountDataException;
import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;
import pe.poc.account.domain.service.AccountDomainService;

/**
 * Implementation of the account management use cases.
 * This service implements the application logic for managing accounts.
 */
@ApplicationScoped
public class AccountManagementService implements IAccountManagementUseCase {

    private final IAccountRepository accountRepository;
    private final AccountDomainService accountDomainService;

    @Inject
    public AccountManagementService(IAccountRepository accountRepository, AccountDomainService accountDomainService) {
        this.accountRepository = accountRepository;
        this.accountDomainService = accountDomainService;
    }

    @Override
    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        // Create domain entity from DTO
        Account account = Account.builder()
                .account(accountRequestDTO.getAccount())
                .number(accountRequestDTO.getNumber())
                .status(accountRequestDTO.getStatus())
                .build();

        // Validate account data
        accountDomainService.validateAccount(account);

        // Validate status
        if (!accountDomainService.isValidStatus(account.getStatus())) {
            throw new InvalidAccountDataException("status", "Invalid status value. Allowed values: ACTIVE, INACTIVE, SUSPENDED");
        }

        // Save account
        Account savedAccount = accountRepository.save(account);

        // Convert to response DTO
        return mapToResponseDTO(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponseDTO updateAccount(Long id, AccountRequestDTO accountRequestDTO) {
        // Find existing account
        Account existingAccount = accountRepository.findById(AccountId.of(id))
                .orElseThrow(() -> new AccountNotFoundException(id));

        // Update account data
        existingAccount.update(
                accountRequestDTO.getAccount(),
                accountRequestDTO.getNumber(),
                accountRequestDTO.getStatus()
        );

        // Validate updated account
        accountDomainService.validateAccount(existingAccount);

        // Validate status
        if (!accountDomainService.isValidStatus(existingAccount.getStatus())) {
            throw new InvalidAccountDataException("status", "Invalid status value. Allowed values: ACTIVE, INACTIVE, SUSPENDED");
        }

        // Save updated account
        Account updatedAccount = accountRepository.update(existingAccount);

        // Convert to response DTO
        return mapToResponseDTO(updatedAccount);
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        // Find account by ID
        Account account = accountRepository.findById(AccountId.of(id))
                .orElseThrow(() -> new AccountNotFoundException(id));

        // Convert to response DTO
        return mapToResponseDTO(account);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        // Get all accounts
        List<Account> accounts = accountRepository.findAll();

        // Convert to response DTOs
        return accounts.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        // Check if account exists
        if (!accountRepository.existsById(AccountId.of(id))) {
            throw new AccountNotFoundException(id);
        }

        // Delete account
        accountRepository.deleteById(AccountId.of(id));
    }

    @Override
    @Transactional
    public AccountResponseDTO activateAccount(Long id) {
        // Find existing account
        Account existingAccount = accountRepository.findById(AccountId.of(id))
                .orElseThrow(() -> new AccountNotFoundException(id));

        // Activate account
        existingAccount.activate();

        // Save updated account
        Account updatedAccount = accountRepository.update(existingAccount);

        // Convert to response DTO
        return mapToResponseDTO(updatedAccount);
    }

    @Override
    @Transactional
    public AccountResponseDTO deactivateAccount(Long id) {
        // Find existing account
        Account existingAccount = accountRepository.findById(AccountId.of(id))
                .orElseThrow(() -> new AccountNotFoundException(id));

        // Deactivate account
        existingAccount.deactivate();

        // Save updated account
        Account updatedAccount = accountRepository.update(existingAccount);

        // Convert to response DTO
        return mapToResponseDTO(updatedAccount);
    }

    /**
     * Maps a domain Account entity to an AccountResponseDTO.
     *
     * @param account The account entity
     * @return The account response DTO
     */
    private AccountResponseDTO mapToResponseDTO(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId().getValue())
                .account(account.getAccount())
                .number(account.getNumber())
                .status(account.getStatus())
                .build();
    }
}