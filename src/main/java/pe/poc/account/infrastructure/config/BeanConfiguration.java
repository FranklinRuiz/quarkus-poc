package pe.poc.account.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import pe.poc.account.domain.service.AccountDomainService;

/**
 * Configuration class for CDI beans.
 * This class registers domain services as CDI beans.
 */
@ApplicationScoped
public class BeanConfiguration {

    /**
     * Produces an AccountDomainService bean.
     *
     * @return The AccountDomainService instance
     */
    @Produces
    @ApplicationScoped
    public AccountDomainService accountDomainService() {
        return new AccountDomainService();
    }
}