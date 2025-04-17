package pe.poc.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.poc.account.domain.model.valueobject.AccountId;

/**
 * Account domain entity representing a bank account.
 * This is the core domain model in the hexagonal architecture.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private AccountId id;
    private String account;
    private String number;
    private String status;

    /**
     * Updates the account information.
     *
     * @param account The account name
     * @param number The account number
     * @param status The account status
     * @return The updated account
     */
    public Account update(String account, String number, String status) {
        this.account = account;
        this.number = number;
        this.status = status;
        return this;
    }

    /**
     * Activates the account by setting its status to "ACTIVE".
     *
     * @return The activated account
     */
    public Account activate() {
        this.status = "ACTIVE";
        return this;
    }

    /**
     * Deactivates the account by setting its status to "INACTIVE".
     *
     * @return The deactivated account
     */
    public Account deactivate() {
        this.status = "INACTIVE";
        return this;
    }
}