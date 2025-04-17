package pe.poc.common.exception;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import pe.poc.account.domain.exception.AccountNotFoundException;
import pe.poc.account.domain.exception.InvalidAccountDataException;

/**
 * Global exception handler for the application.
 * This class maps exceptions to HTTP responses.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof AccountNotFoundException) {
            return buildResponse(Status.NOT_FOUND, exception.getMessage());
        } else if (exception instanceof InvalidAccountDataException) {
            return buildResponse(Status.BAD_REQUEST, exception.getMessage());
        } else {
            return buildResponse(Status.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    /**
     * Builds a response with the given status and message.
     *
     * @param status The HTTP status
     * @param message The error message
     * @return The response
     */
    private Response buildResponse(Status status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(status.getStatusCode(), message);
        return Response.status(status).entity(errorResponse).build();
    }

    /**
     * Error response DTO.
     */
    @RegisterForReflection
    public static class ErrorResponse {
        private final int status;
        private final String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
