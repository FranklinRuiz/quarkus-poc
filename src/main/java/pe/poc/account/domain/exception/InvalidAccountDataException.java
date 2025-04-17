package pe.poc.account.domain.exception;

/**
 * Exception thrown when account data is invalid.
 * This is a domain exception that represents a specific business rule violation.
 */
public class InvalidAccountDataException extends RuntimeException {
    
    public InvalidAccountDataException(String message) {
        super(message);
    }
    
    public InvalidAccountDataException(String field, String message) {
        super("Invalid account data for field '" + field + "': " + message);
    }
}