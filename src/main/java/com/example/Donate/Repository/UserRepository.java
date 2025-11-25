package com.example.Donate.Repository;

import com.example.Donate.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByVerificationCode(String verificationCode); // thêm dòng này
    User findByUsernameAndPassword(String username, String password);

}
