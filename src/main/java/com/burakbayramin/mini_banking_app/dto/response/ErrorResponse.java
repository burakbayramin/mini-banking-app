package com.burakbayramin.mini_banking_app.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        String apiPath,
        HttpStatus errorCode,
        String errorMessage,
        LocalDateTime errorTime
) {
}
