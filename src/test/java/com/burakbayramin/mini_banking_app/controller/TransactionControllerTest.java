package com.burakbayramin.mini_banking_app.controller;

import com.burakbayramin.mini_banking_app.dto.request.TransactionRequest;
import com.burakbayramin.mini_banking_app.model.Transaction;
import com.burakbayramin.mini_banking_app.service.impl.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TransactionServiceImpl transactionService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void testInitiateMoneyTransfer_Success() throws Exception {
//        TransactionRequest transferRequest = new TransactionRequest();
//        transferRequest.setFromAccountId("ACC123");
//        transferRequest.setToAccountId("ACC456");
//        transferRequest.setAmount(new BigDecimal("500.00"));
//
//        Transaction mockTransaction = new Transaction();
//        mockTransaction.setId(1L);
//        mockTransaction.setFromAccountId("ACC123");
//        mockTransaction.setToAccountId("ACC456");
//        mockTransaction.setAmount(new BigDecimal("500.00"));
//        mockTransaction.setTransactionDate(LocalDateTime.now());
//        mockTransaction.setStatus("SUCCESS");
//
//        Mockito.when(transactionService.transferMoney(any(TransferRequest.class))).thenReturn(mockTransaction);
//
//        mockMvc.perform(post("/api/transactions/transfer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transferRequest)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value("SUCCESS"))
//                .andExpect(jsonPath("$.amount").value(500.00));
//    }
//
//    @Test
//    public void testInitiateMoneyTransfer_Failure() throws Exception {
//        TransferRequest transferRequest = new TransferRequest();
//        transferRequest.setFromAccountId("ACC123");
//        transferRequest.setToAccountId("ACC456");
//        transferRequest.setAmount(new BigDecimal("1500.00")); // Assuming insufficient funds
//
//        Mockito.when(transactionService.transferMoney(any(TransferRequest.class)))
//                .thenThrow(new RuntimeException("Insufficient funds"));
//
//        mockMvc.perform(post("/api/transactions/transfer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(transferRequest)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Insufficient funds"));
//    }
//
//    @Test
//    public void testViewTransactionHistory_Success() throws Exception {
//        String accountId = "ACC123";
//
//        Transaction tx1 = new Transaction();
//        tx1.setId(1L);
//        tx1.setFromAccountId("ACC123");
//        tx1.setToAccountId("ACC456");
//        tx1.setAmount(new BigDecimal("500.00"));
//        tx1.setTransactionDate(LocalDateTime.now().minusDays(1));
//        tx1.setStatus("SUCCESS");
//
//        Transaction tx2 = new Transaction();
//        tx2.setId(2L);
//        tx2.setFromAccountId("ACC789");
//        tx2.setToAccountId("ACC123");
//        tx2.setAmount(new BigDecimal("300.00"));
//        tx2.setTransactionDate(LocalDateTime.now().minusDays(2));
//        tx2.setStatus("SUCCESS");
//
//        List<Transaction> transactions = Arrays.asList(tx1, tx2);
//
//        Mockito.when(transactionService.getTransactionHistory(eq(accountId))).thenReturn(transactions);
//
//        mockMvc.perform(get("/api/transactions/account/{accountId}", accountId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].fromAccountId").value("ACC123"))
//                .andExpect(jsonPath("$[1].toAccountId").value("ACC123"));
//    }
}
