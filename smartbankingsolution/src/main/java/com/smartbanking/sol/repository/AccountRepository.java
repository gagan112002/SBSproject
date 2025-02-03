package com.smartbanking.sol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartbanking.sol.model.Accounts;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {

    // Find an account by its account number
    Accounts findByAccountNumber(String accountNumber);

    // Find all accounts by the user ID (assuming the User class is present)
    List<Accounts> findByUserUserId(long userId);

    // Find all active accounts (if needed)
    List<Accounts> findByIsActive(boolean isActive);

    // Find account by user ID and account type (e.g., saving, checking, etc.)
    List<Accounts> findByUserUserIdAndAccountType(long userId, String accountType);
}
