package com.smartbanking.sol.repository;

import com.smartbanking.sol.model.Loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {

    // Find all loans by user ID
    List<Loans> findByUserUserId(Long userId);

    // Find all loans by status (PENDING, APPROVED, REJECTED, CLOSED)
    List<Loans> findByStatus(String status);
}
