package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.model.Account;
import com.burakbayramin.mini_banking_app.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AccountServiceImpl accountService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testCreateAccount_Success() throws Exception {
//        Account newAccount = new Account();
//        newAccount.setNumber("ACC123456");
//        newAccount.setName("Savings");
//        newAccount.setBalance(new BigDecimal("1000.00"));
//
//        Account savedAccount = new Account();
//        savedAccount.setId(UUID.randomUUID());
//        savedAccount.setNumber(newAccount.getNumber());
//        savedAccount.setName(newAccount.getName());
//        savedAccount.setBalance(newAccount.getBalance());
//        savedAccount.setCreatedAt(LocalDateTime.now());
//        savedAccount.setUpdatedAt(LocalDateTime.now());
//
//        Mockito.when(accountService.createAccount(any(Account.class));).thenReturn(savedAccount);
//
//        mockMvc.perform(post("/api/accounts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newAccount)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.number").value("ACC123456"))
//                .andExpect(jsonPath("$.name").value("Savings"))
//                .andExpect(jsonPath("$.balance").value(1000.00));
//    }
//
//    @Test
//    public void testGetAccountDetails_Success() throws Exception {
//        UUID accountId = UUID.randomUUID();
//        Account account = new Account();
//        account.setId(accountId);
//        account.setNumber("ACC123456");
//        account.setName("Savings");
//        account.setBalance(new BigDecimal("1500.00"));
//        account.setCreatedAt(LocalDateTime.now());
//        account.setUpdatedAt(LocalDateTime.now());
//
//        Mockito.when(accountService.getAccountDetails(eq(accountId))).thenReturn(account);
//
//        mockMvc.perform(get("/api/accounts/{id}", accountId.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.number").value("ACC123456"))
//                .andExpect(jsonPath("$.balance").value(1500.00));
//    }
//
//    @Test
//    public void testDeleteAccount_Success() throws Exception {
//        UUID accountId = UUID.randomUUID();
//
//        Mockito.doNothing().when(accountService).deleteAccount(eq(accountId));
//
//        mockMvc.perform(delete("/api/accounts/{id}", accountId.toString()))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    public void testUpdateAccount_Success() throws Exception {
//        UUID accountId = UUID.randomUUID();
//        Account updatedAccount = new Account();
//        updatedAccount.setName("Checking");
//        updatedAccount.setBalance(new BigDecimal("2000.00"));
//
//        Account savedAccount = new Account();
//        savedAccount.setId(accountId);
//        savedAccount.setNumber("ACC123456");
//        savedAccount.setName(updatedAccount.getName());
//        savedAccount.setBalance(updatedAccount.getBalance());
//        savedAccount.setCreatedAt(LocalDateTime.now());
//        savedAccount.setUpdatedAt(LocalDateTime.now());
//
//        Mockito.when(accountService.updateAccount(eq(accountId), any(Account.class))).thenReturn(savedAccount);
//
//        mockMvc.perform(put("/api/accounts/{id}", accountId.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedAccount)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Checking"))
//                .andExpect(jsonPath("$.balance").value(2000.00));
//    }
}
