package com.smartbanking.sol.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartbanking.sol.model.Transactions;
import com.smartbanking.sol.services.TransactionService;

@RestController
@RequestMapping("/transact")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Get transaction history by user ID
    @GetMapping("/transactionHistory/{userId}")
    public ResponseEntity<List<Transactions>> getTransactionHistory(@PathVariable long userId) {
        List<Transactions> transactions = transactionService.getTransactionsByAccountId(userId);
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    // Get transactions by a date range
    @GetMapping("/transactionHistory/{userId}/dateRange")
    public ResponseEntity<List<Transactions>> getTransactionsByDateRange(
            @PathVariable long userId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        
        List<Transactions> transactions = transactionService.getTransactionsByDateRange(userId, start, end);
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(transactions);
        }
    }

    // Create a new transaction (deposit, withdrawal, etc.)
    @PostMapping("/create/{userId}")
    public ResponseEntity<Transactions> createTransaction(
            @PathVariable long userId,
            @RequestParam("transactionType") String transactionType,
            @RequestParam("amount") BigDecimal amount) {

        Transactions transaction = transactionService.createTransaction(userId, transactionType, amount);
        if (transaction != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Update transaction status (approve or reject)
    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Transactions> updateTransactionStatus(
            @PathVariable long transactionId,
            @RequestParam("approved") boolean approved) {

        Transactions updatedTransaction = transactionService.updateTransactionStatus(transactionId, approved);
        if (updatedTransaction != null) {
            return ResponseEntity.ok(updatedTransaction);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Cancel a transaction (rollback logic can be added if necessary)
    @DeleteMapping("/cancel/{transactionId}")
    public ResponseEntity<String> cancelTransaction(@PathVariable long transactionId) {
        try {
            transactionService.cancelTransaction(transactionId);
            return ResponseEntity.ok("Transaction canceled successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }
    }

    // Get total amount of transactions for a user (deposit + withdrawal)
    @GetMapping("/totalAmount/{userId}")
    public ResponseEntity<BigDecimal> getTotalTransactionAmount(@PathVariable long accountId) {
        BigDecimal totalAmount = transactionService.getTotalTransactionAmount(accountId);
        return ResponseEntity.ok(totalAmount);
    }
}
