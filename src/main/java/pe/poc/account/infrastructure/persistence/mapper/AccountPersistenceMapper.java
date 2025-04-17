package pe.poc.account.infrastructure.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;
import pe.poc.account.infrastructure.persistence.entity.AccountJpaEntity;

/**
 * Mapper for converting between Account domain model and AccountJpaEntity.
 */
@ApplicationScoped
public class AccountPersistenceMapper {

    /**
     * Maps a JPA entity to a domain model.
     *
     * @param jpaEntity The JPA entity
     * @return The domain model
     */
    public Account toDomainModel(AccountJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }
        
        return Account.builder()
                .id(jpaEntity.getId() != null ? AccountId.of(jpaEntity.getId()) : null)
                .account(jpaEntity.getAccount())
                .number(jpaEntity.getNumber())
                .status(jpaEntity.getStatus())
                .build();
    }

    /**
     * Maps a domain model to a JPA entity.
     *
     * @param domainModel The domain model
     * @return The JPA entity
     */
    public AccountJpaEntity toJpaEntity(Account domainModel) {
        if (domainModel == null) {
            return null;
        }
        
        return AccountJpaEntity.builder()
                .id(domainModel.getId() != null ? domainModel.getId().getValue() : null)
                .account(domainModel.getAccount())
                .number(domainModel.getNumber())
                .status(domainModel.getStatus())
                .build();
    }
}