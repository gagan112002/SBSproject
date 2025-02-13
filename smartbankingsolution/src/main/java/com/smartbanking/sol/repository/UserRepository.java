package com.smartbanking.sol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbanking.sol.model.User;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserEmail(String userEmail);
}
