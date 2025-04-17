package pe.poc.account.domain.exception;

/**
 * Exception thrown when an account is not found.
 * This is a domain exception that represents a specific business rule violation.
 */
public class AccountNotFoundException extends RuntimeException {
    
    public AccountNotFoundException(Long id) {
        super("Account with ID " + id + " not found");
    }
    
    public AccountNotFoundException(String message) {
        super(message);
    }
}