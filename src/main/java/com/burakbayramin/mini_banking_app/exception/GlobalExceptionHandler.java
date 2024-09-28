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

    // 1. UserAlreadyExistsException Handler
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        logger.error("UserAlreadyExistsException: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                request.getRequestURI(),
                HttpStatus.CONFLICT,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // 2. ResourceNotFoundException Handler
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

    // 3. BadRequestException Handler
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

    // 4. ConflictException Handler
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

    // 5. UnauthorizedException Handler
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

    // 6. MethodArgumentNotValidException Handler (DTO Doğrulama Hataları)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logger.error("MethodArgumentNotValidException: {}", ex.getMessage());

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

    // 7. AccessDeniedException Handler (Spring Security)
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

    // Genel İstisnalar için Handler
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
