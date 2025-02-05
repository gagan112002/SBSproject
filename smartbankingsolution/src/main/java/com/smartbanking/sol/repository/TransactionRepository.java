package com.smartbanking.sol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbanking.sol.model.TransactionStatus;
import com.smartbanking.sol.model.Transactions;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    // Find all transactions by account ID (assuming `account` is related to `Account` object)
    List<Transactions> findByAccountAccountId(long accountId);

    // Find all transactions by account ID and filter by transaction date range
    List<Transactions> findByAccountAccountIdAndTransactionDateBetween(long accountId, LocalDateTime startDate, LocalDateTime endDate);

    // Find all transactions by user ID (user accessed via Account)
    List<Transactions> findByAccount_User_UserId(long userId);  // Updated query

    // Find all transactions by user ID and filter by transaction date range (user accessed via Account)
    List<Transactions> findByAccount_User_UserIdAndTransactionDateBetween(long userId, LocalDateTime startDate, LocalDateTime endDate);  // Updated query

    // Find all transactions by a specific status (approved or rejected)
    List<Transactions> findByStatus(TransactionStatus status);

    // Find all transactions by account ID and a specific transaction type (e.g., deposit, withdrawal)
    List<Transactions> findByAccountAccountIdAndTransactionType(long accountId, String transactionType);
}
