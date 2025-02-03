package com.smartbanking.sol.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    @NotNull(message = "User name cannot be null")
    private String userName;

    @Column(nullable = false)
    @NotNull(message = "Password cannot be null")
    private String password;

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String userEmail;

    @Column(length = 10, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String userPhoneNo;

    private long userAdharNo;
    private long userPanNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private boolean isApproved;

    // Getters and Setters
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public long getUserAdharNo() {
        return userAdharNo;
    }

    public void setUserAdharNo(long userAdharNo) {
        this.userAdharNo = userAdharNo;
    }

    public long getUserPanNo() {
        return userPanNo;
    }

    public void setUserPanNo(long userPanNo) {
        this.userPanNo = userPanNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }
    
}
