	package com.smartbanking.sol.controller;
	
	import com.smartbanking.sol.model.User;
	import com.smartbanking.sol.services.AdminServices;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import java.util.List;
	
	@RestController
	@RequestMapping("/api/admin")
	public class AdminController {
	
	    @Autowired
	    private AdminServices adminService;
	
	    // 1. Get All Users
	    @GetMapping("/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        return ResponseEntity.ok(adminService.getAllUsers());
	    }
	
	    // 2. Get User by ID
	    @GetMapping("/users/{userId}")
	    public ResponseEntity<User> getUserById(@PathVariable long userId) {
	        return ResponseEntity.ok(adminService.getUserById(userId).get());
	    }
	
	    // 3. Delete User
	    @DeleteMapping("/users/{userId}")
	    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
	        adminService.deleteUser(userId);
	        return ResponseEntity.ok("User deleted successfully");
	    }
	
	    // 4. Approve or Reject User Registration
	    @PostMapping("/users/{userId}/status")
	    public ResponseEntity<String> updateUserStatus(@PathVariable long userId, @RequestParam boolean approved) {
	        adminService.updateUserStatus(userId, approved);
	        return ResponseEntity.ok(approved ? "User approved" : "User rejected");
	    }
	
	    // 5. Reset User Password
	    @PutMapping("/users/{userId}/reset-password")
	    public ResponseEntity<String> resetUserPassword(@PathVariable long userId, @RequestParam String newPassword) {
	        adminService.resetUserPassword(userId, newPassword);
	        return ResponseEntity.ok("Password reset successfully");
	    }
	
	    // 6. Get All Transactions
	    @GetMapping("/transactions")
	    public ResponseEntity<List<?>> getAllTransactions() {
	        return ResponseEntity.ok(adminService.getAllTransactions());
	    }
	
	    // 7. Approve or Reject Transactions
	    @PostMapping("/transactions/{transactionId}/status")
	    public ResponseEntity<String> updateTransactionStatus(@PathVariable long transactionId, @RequestParam boolean approved) {
	        adminService.updateTransactionStatus(transactionId, approved);
	        return ResponseEntity.ok(approved ? "Transaction approved" : "Transaction rejected");
	    }
	
	    // 8. Get All Loan Applications
	    @GetMapping("/loans")
	    public ResponseEntity<List<?>> getAllLoanApplications() {
	        return ResponseEntity.ok(adminService.getAllLoans());
	    }
	
	    // 9. Approve or Reject Loan Applications
	    @PostMapping("/loans/{loanId}/status")
	    public ResponseEntity<String> updateLoanStatus(@PathVariable long loanId, @RequestParam boolean approved) {
	        adminService.updateLoanStatus(loanId, approved);
	        return ResponseEntity.ok(approved ? "Loan approved" : "Loan rejected");
	    }
	}
