package pe.poc.account.application.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for account responses.
 * This DTO is used to transfer account data from the application layer to the client.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class AccountResponseDTO {

    /**
     * The account ID.
     */
    private Long id;

    /**
     * The account name.
     */
    private String account;

    /**
     * The account number.
     */
    private String number;

    /**
     * The account status.
     */
    private String status;
}
