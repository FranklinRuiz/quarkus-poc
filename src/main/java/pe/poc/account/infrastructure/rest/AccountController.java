package pe.poc.account.infrastructure.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import pe.poc.account.application.dto.AccountRequestDTO;
import pe.poc.account.application.dto.AccountResponseDTO;
import pe.poc.account.application.port.input.IAccountManagementUseCase;

/**
 * REST controller for account management.
 * This controller exposes the API endpoints for the account management use cases.
 */
@Path("/api/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountController {

    private final IAccountManagementUseCase accountManagementUseCase;

    @Inject
    public AccountController(IAccountManagementUseCase accountManagementUseCase) {
        this.accountManagementUseCase = accountManagementUseCase;
    }

    /**
     * Creates a new account.
     *
     * @param accountRequestDTO The account data
     * @return The created account
     */
    @POST
    public Response createAccount(@Valid AccountRequestDTO accountRequestDTO) {
        AccountResponseDTO createdAccount = accountManagementUseCase.createAccount(accountRequestDTO);
        return Response.status(Status.CREATED).entity(createdAccount).build();
    }

    /**
     * Updates an existing account.
     *
     * @param id The account ID
     * @param accountRequestDTO The updated account data
     * @return The updated account
     */
    @PUT
    @Path("/{id}")
    public Response updateAccount(@PathParam("id") Long id, @Valid AccountRequestDTO accountRequestDTO) {
        AccountResponseDTO updatedAccount = accountManagementUseCase.updateAccount(id, accountRequestDTO);
        return Response.ok(updatedAccount).build();
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id The account ID
     * @return The account
     */
    @GET
    @Path("/{id}")
    public Response getAccountById(@PathParam("id") Long id) {
        AccountResponseDTO account = accountManagementUseCase.getAccountById(id);
        return Response.ok(account).build();
    }

    /**
     * Retrieves all accounts.
     *
     * @return A list of all accounts
     */
    @GET
    public Response getAllAccounts() {
        List<AccountResponseDTO> accounts = accountManagementUseCase.getAllAccounts();
        return Response.ok(accounts).build();
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id The account ID
     * @return No content response
     */
    @DELETE
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") Long id) {
        accountManagementUseCase.deleteAccount(id);
        return Response.noContent().build();
    }

    /**
     * Activates an account by its ID.
     *
     * @param id The account ID
     * @return The activated account
     */
    @PUT
    @Path("/{id}/activate")
    public Response activateAccount(@PathParam("id") Long id) {
        AccountResponseDTO activatedAccount = accountManagementUseCase.activateAccount(id);
        return Response.ok(activatedAccount).build();
    }

    /**
     * Deactivates an account by its ID.
     *
     * @param id The account ID
     * @return The deactivated account
     */
    @PUT
    @Path("/{id}/deactivate")
    public Response deactivateAccount(@PathParam("id") Long id) {
        AccountResponseDTO deactivatedAccount = accountManagementUseCase.deactivateAccount(id);
        return Response.ok(deactivatedAccount).build();
    }
}