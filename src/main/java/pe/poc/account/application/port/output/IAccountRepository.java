package pe.poc.account.application.port.output;

import java.util.List;
import java.util.Optional;

import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;

/**
 * Repository interface for Account entities.
 * This is an output port in the hexagonal architecture that will be implemented
 * by the infrastructure layer.
 */
public interface IAccountRepository {
    
    /**
     * Saves an account to the repository.
     *
     * @param account The account to save
     * @return The saved account with its ID
     */
    Account save(Account account);
    
    /**
     * Updates an existing account in the repository.
     *
     * @param account The account to update
     * @return The updated account
     */
    Account update(Account account);
    
    /**
     * Finds an account by its ID.
     *
     * @param id The account ID
     * @return An Optional containing the account if found, empty otherwise
     */
    Optional<Account> findById(AccountId id);
    
    /**
     * Retrieves all accounts from the repository.
     *
     * @return A list of all accounts
     */
    List<Account> findAll();
    
    /**
     * Deletes an account from the repository.
     *
     * @param id The ID of the account to delete
     */
    void deleteById(AccountId id);
    
    /**
     * Checks if an account with the given ID exists.
     *
     * @param id The account ID
     * @return true if the account exists, false otherwise
     */
    boolean existsById(AccountId id);
}