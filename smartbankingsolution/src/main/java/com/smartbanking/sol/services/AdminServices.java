package com.smartbanking.sol.services;

import com.smartbanking.sol.model.User;
import com.smartbanking.sol.model.Transactions;
import com.smartbanking.sol.model.Loans;
import com.smartbanking.sol.repository.UserRepository;
import com.smartbanking.sol.repository.TransactionRepository;
import com.smartbanking.sol.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private LoanRepository loanRepository;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // User Management
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
    
    public User approveUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setApproved(true);
            return userRepository.save(user.get());
        }
        return null;
    }
    
    public void resetUserPassword(long id, String newPassword) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user.get());
        }
    }

    // Transaction Handling
    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transactions approveTransaction(long transactionId) {
        Optional<Transactions> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            transaction.get().setApproved(true);
            return transactionRepository.save(transaction.get());
        }
        return null;
    }
    
    // Loan Processing
    public List<Loans> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loans approveLoan(long loanId) {
        Optional<Loans> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            loan.get().setApproved(true);
            return loanRepository.save(loan.get());
        }
        return null;
    }
    
    public void updateLoanStatus(long loanId, boolean approved) {
        Optional<Loans> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            Loans currentLoan = loan.get();
            currentLoan.setApproved(approved); // This method will update the status of the loan
            loanRepository.save(currentLoan); // Save the updated loan to the database
        } else {
            throw new IllegalArgumentException("Loan not found with ID: " + loanId);
        }
    }
    public void updateTransactionStatus(long transactionId, boolean approved) {
        Optional<Transactions> transaction = transactionRepository.findById(transactionId);
        if (transaction.isPresent()) {
            Transactions currentTransaction = transaction.get();
            currentTransaction.setApproved(approved); // This method will update the status of the transaction
            transactionRepository.save(currentTransaction); // Save the updated transaction to the database
        } else {
            throw new IllegalArgumentException("Transaction not found with ID: " + transactionId);
        }
    }
    
    public void updateUserStatus(long userId, boolean approved) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setApproved(approved); // Update the approval status of the user
            userRepository.save(currentUser); // Save the updated user to the database
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }
}