package pe.poc.account.application.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for account creation and update requests.
 * This DTO is used to transfer data from the client to the application layer.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class AccountRequestDTO {

    /**
     * The account name.
     */
    @NotBlank(message = "Account name cannot be empty")
    private String account;

    /**
     * The account number.
     */
    @NotBlank(message = "Account number cannot be empty")
    private String number;

    /**
     * The account status (ACTIVE, INACTIVE, SUSPENDED).
     */
    @NotBlank(message = "Account status cannot be empty")
    private String status;
}
