package pe.poc.account.application.port.input;

import java.util.List;

import pe.poc.account.application.dto.AccountRequestDTO;
import pe.poc.account.application.dto.AccountResponseDTO;

/**
 * Interface defining the account management use cases.
 * This is an input port in the hexagonal architecture that will be implemented
 * by the application service.
 */
public interface IAccountManagementUseCase {
    
    /**
     * Creates a new account.
     *
     * @param accountRequestDTO The account data
     * @return The created account
     */
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);
    
    /**
     * Updates an existing account.
     *
     * @param id The account ID
     * @param accountRequestDTO The updated account data
     * @return The updated account
     */
    AccountResponseDTO updateAccount(Long id, AccountRequestDTO accountRequestDTO);
    
    /**
     * Retrieves an account by its ID.
     *
     * @param id The account ID
     * @return The account
     */
    AccountResponseDTO getAccountById(Long id);
    
    /**
     * Retrieves all accounts.
     *
     * @return A list of all accounts
     */
    List<AccountResponseDTO> getAllAccounts();
    
    /**
     * Deletes an account by its ID.
     *
     * @param id The account ID
     */
    void deleteAccount(Long id);
    
    /**
     * Activates an account by setting its status to "ACTIVE".
     *
     * @param id The account ID
     * @return The activated account
     */
    AccountResponseDTO activateAccount(Long id);
    
    /**
     * Deactivates an account by setting its status to "INACTIVE".
     *
     * @param id The account ID
     * @return The deactivated account
     */
    AccountResponseDTO deactivateAccount(Long id);
}