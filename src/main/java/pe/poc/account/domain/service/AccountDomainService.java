package pe.poc.account.domain.service;

import pe.poc.account.domain.exception.InvalidAccountDataException;
import pe.poc.account.domain.model.Account;

/**
 * Domain service containing business logic for Account entities.
 * This service implements core business rules and validations.
 */
public class AccountDomainService {

    /**
     * Validates account data according to business rules.
     *
     * @param account The account to validate
     * @throws InvalidAccountDataException if validation fails
     */
    public void validateAccount(Account account) {
        if (account == null) {
            throw new InvalidAccountDataException("Account cannot be null");
        }

        if (account.getAccount() == null || account.getAccount().trim().isEmpty()) {
            throw new InvalidAccountDataException("account", "Account name cannot be empty");
        }

        if (account.getNumber() == null || account.getNumber().trim().isEmpty()) {
            throw new InvalidAccountDataException("number", "Account number cannot be empty");
        }

        if (account.getStatus() == null || account.getStatus().trim().isEmpty()) {
            throw new InvalidAccountDataException("status", "Account status cannot be empty");
        }
    }

    /**
     * Validates if the account status is valid.
     *
     * @param status The status to validate
     * @return true if the status is valid, false otherwise
     */
    public boolean isValidStatus(String status) {
        if (status == null) {
            return false;
        }
        
        return status.equals("ACTIVE") || status.equals("INACTIVE") || status.equals("SUSPENDED");
    }
}