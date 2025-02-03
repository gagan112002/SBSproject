package com.smartbanking.sol.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartbanking.sol.model.Accounts;
import com.smartbanking.sol.model.Transactions;
import com.smartbanking.sol.model.TransactionStatus;
import com.smartbanking.sol.repository.TransactionRepository;
import com.smartbanking.sol.repository.AccountRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // Get transactions by account ID
    public List<Transactions> getTransactionsByAccountId(long accountId) {
        return transactionRepository.findByAccountAccountId(accountId);
    }

    // Get transactions by account ID and date range
    public List<Transactions> getTransactionsByDateRange(long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByAccountAccountIdAndTransactionDateBetween(accountId, startDate, endDate);
    }

    // Create a new transaction (deposit, withdrawal, etc.)
    public Transactions createTransaction(long accountId, String transactionType, BigDecimal amount) {
        Optional<Accounts> accountOpt = accountRepository.findById(accountId);
        if (!accountOpt.isPresent()) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId);
        }

        Accounts account = accountOpt.get();

        Transactions transaction = new Transactions();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING); // Initial status is PENDING

        return transactionRepository.save(transaction);
    }

    // Update the status of a transaction (approve or reject)
    public Transactions updateTransactionStatus(long transactionId, boolean isApproved) {
        Optional<Transactions> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transactions transaction = transactionOpt.get();
            transaction.setApproved(isApproved); // Set status to APPROVED or REJECTED
            return transactionRepository.save(transaction);
        } else {
            throw new IllegalArgumentException("Transaction not found with ID: " + transactionId);
        }
    }

    // Cancel a transaction (delete the transaction)
    public boolean cancelTransaction(long transactionId) {
        Optional<Transactions> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            transactionRepository.deleteById(transactionId);
            return true;  // Transaction successfully canceled
        } else {
            return false;  // Transaction not found
        }
    }

    // Get the total amount of transactions for an account
    public BigDecimal getTotalTransactionAmount(long accountId) {
        List<Transactions> transactions = transactionRepository.findByAccountAccountId(accountId);
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Transactions transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
        }
        return totalAmount;
    }

    // Get total deposit amount for an account
    public BigDecimal getTotalDepositAmount(long accountId) {
        List<Transactions> transactions = transactionRepository.findByAccountAccountIdAndTransactionType(accountId, "DEPOSIT");
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Transactions transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
        }
        return totalAmount;
    }

    // Get total withdrawal amount for an account
    public BigDecimal getTotalWithdrawalAmount(long accountId) {
        List<Transactions> transactions = transactionRepository.findByAccountAccountIdAndTransactionType(accountId, "WITHDRAWAL");
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Transactions transaction : transactions) {
            totalAmount = totalAmount.add(transaction.getAmount());
        }
        return totalAmount;
    }
}
