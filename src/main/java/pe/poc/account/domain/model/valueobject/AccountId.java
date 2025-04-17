package pe.poc.account.domain.model.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Value object representing the unique identifier of an Account.
 * This follows Domain-Driven Design principles where IDs are treated as value objects.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccountId {
    private Long value;

    /**
     * Factory method to create a new AccountId.
     *
     * @param id The ID value
     * @return A new AccountId instance
     */
    public static AccountId of(Long id) {
        return new AccountId(id);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "null";
    }
}