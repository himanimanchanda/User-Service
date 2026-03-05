package com.appointment.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 404 Handler (Resource Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleNotFound(ResourceNotFoundException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 2. 409 Handler (User or Organisation Already Exists)
    // Super Admin jab duplicate Org banayega tab bhi yahi call hoga
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiExceptionResponse> handleConflict(UserAlreadyExistsException ex){
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // 3. 403 Forbidden (Security Role Mismatch)
    // Ye tab trigger hoga jab koi normal user /api/superadmin access karega
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiExceptionResponse> handleAccessDenied(Exception ex){
        return buildResponse("You don't have permission to access this resource.", HttpStatus.FORBIDDEN);
    }

    // 4. 401 Unauthorized (Invalid Login)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> handleInvalidCredentials(InvalidCredentialsException ex){
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 5. 403 Forbidden (Account Banned/Disabled)
    @ExceptionHandler(AccountDisabledException.class)
    public ResponseEntity<ApiExceptionResponse> handleAccountDisabled(AccountDisabledException ex){
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    // 6. Generic Handler (Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGeneric(Exception ex){
        // Print stack trace for debugging
        return buildResponse("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper Method to reduce code duplication
    private ResponseEntity<ApiExceptionResponse> buildResponse(String message, HttpStatus status) {
        ApiExceptionResponse error = new ApiExceptionResponse(
                LocalDateTime.now(),
                message,
                status.value()
        );
        return new ResponseEntity<>(error, status);
    }
}
