package com.burakbayramin.mini_banking_app.dto.request;

import lombok.Data;

@Data
public class AccountSearchRequest {
    private String number;
    private String name;
}
