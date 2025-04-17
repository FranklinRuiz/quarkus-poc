package pe.poc.account.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA entity for Account.
 * This entity is used for persistence in the database.
 */
@Entity
@Table(name = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountJpaEntity {
    
    /**
     * The account ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * The account name.
     */
    @Column(name = "account", nullable = false)
    private String account;
    
    /**
     * The account number.
     */
    @Column(name = "number", nullable = false)
    private String number;
    
    /**
     * The account status.
     */
    @Column(name = "status", nullable = false)
    private String status;
}