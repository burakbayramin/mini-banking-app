package com.burakbayramin.mini_banking_app.exception;

import com.burakbayramin.mini_banking_app.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles UserAlreadyExistsException.
     *
     * @param ex      the exception thrown when a user already exists
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 409 (Conflict)
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        // Log the exception message at the error level
        logger.error("UserAlreadyExistsException: {}", ex.getMessage());

        // Create an ErrorResponse object with relevant details
        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(), // The URI where the exception occurred
                HttpStatus.CONFLICT,     // HTTP status code 409
                ex.getMessage(),         // The exception message
                LocalDateTime.now()      // Timestamp of the error
        );

        // Return the ErrorResponse wrapped in a ResponseEntity with status 409
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles ResourceNotFoundException.
     *
     * @param ex      the exception thrown when a requested resource is not found
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.error("ResourceNotFoundException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BadRequestException.
     *
     * @param ex      the exception thrown for bad requests
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        logger.error("BadRequestException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ConflictException.
     *
     * @param ex      the exception thrown when a conflict occurs
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 409 (Conflict)
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException ex, HttpServletRequest request) {
        logger.error("ConflictException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handles UnauthorizedException.
     *
     * @param ex      the exception thrown when unauthorized access is attempted
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 401 (Unauthorized)
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request) {
        logger.error("UnauthorizedException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles MethodArgumentNotValidException.
     * This exception is thrown when validation on an argument annotated with @Valid fails.
     *
     * @param ex      the exception containing validation errors
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage());

        // Collect validation errors into a map
        Map<String, String> validationErrors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST,
                validationErrors.toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles AccessDeniedException.
     *
     * @param ex      the exception thrown when access is denied
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 403 (Forbidden)
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        logger.error("AccessDeniedException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.FORBIDDEN,
                "Access Denied",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

//    // 8. Genel Exception Handler
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
//        logger.error("Unhandled exception: ", ex);
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                request.getRequestURI(),
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "An unexpected error occurred: " + ex.getMessage(),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    // Method Argument Validation Hataları için Handler
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                request.getRequestURI(),
//                HttpStatus.BAD_REQUEST,
//                errors.toString(),
//                LocalDateTime.now()
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

    /**
     * Handles all other exceptions not specifically handled by other methods.
     *
     * @param ex      the exception thrown
     * @param request the HTTP request that resulted in the exception
     * @return a ResponseEntity containing the ErrorResponse with HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
