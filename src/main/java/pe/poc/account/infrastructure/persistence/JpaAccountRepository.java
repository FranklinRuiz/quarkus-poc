package pe.poc.account.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import pe.poc.account.application.port.output.IAccountRepository;
import pe.poc.account.domain.model.Account;
import pe.poc.account.domain.model.valueobject.AccountId;
import pe.poc.account.infrastructure.persistence.entity.AccountJpaEntity;
import pe.poc.account.infrastructure.persistence.mapper.AccountPersistenceMapper;

/**
 * JPA implementation of the IAccountRepository interface.
 * This class adapts the JPA repository to the domain repository interface.
 */
@ApplicationScoped
public class JpaAccountRepository implements IAccountRepository {

    private final EntityManager entityManager;
    private final AccountPersistenceMapper mapper;

    @Inject
    public JpaAccountRepository(EntityManager entityManager, AccountPersistenceMapper mapper) {
        this.entityManager = entityManager;
        this.mapper = mapper;
    }

    @Override
    public Account save(Account account) {
        AccountJpaEntity jpaEntity = mapper.toJpaEntity(account);
        entityManager.persist(jpaEntity);
        return mapper.toDomainModel(jpaEntity);
    }

    @Override
    public Account update(Account account) {
        AccountJpaEntity jpaEntity = mapper.toJpaEntity(account);
        jpaEntity = entityManager.merge(jpaEntity);
        return mapper.toDomainModel(jpaEntity);
    }

    @Override
    public Optional<Account> findById(AccountId id) {
        AccountJpaEntity jpaEntity = entityManager.find(AccountJpaEntity.class, id.getValue());
        return Optional.ofNullable(mapper.toDomainModel(jpaEntity));
    }

    @Override
    public List<Account> findAll() {
        TypedQuery<AccountJpaEntity> query = entityManager.createQuery(
                "SELECT a FROM AccountJpaEntity a", AccountJpaEntity.class);
        List<AccountJpaEntity> jpaEntities = query.getResultList();
        return jpaEntities.stream()
                .map(mapper::toDomainModel)
                .toList();
    }

    @Override
    public void deleteById(AccountId id) {
        AccountJpaEntity jpaEntity = entityManager.find(AccountJpaEntity.class, id.getValue());
        if (jpaEntity != null) {
            entityManager.remove(jpaEntity);
        }
    }

    @Override
    public boolean existsById(AccountId id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(a) FROM AccountJpaEntity a WHERE a.id = :id", Long.class);
        query.setParameter("id", id.getValue());
        return query.getSingleResult() > 0;
    }
}