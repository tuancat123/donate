package com.example.Donate.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(name="full_name", length = 100)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role = Role.USER;
    private Timestamp createdAt;

    @Column(name="enabled")
    private boolean enabled = false; // mặc định chưa xác thực

    @Column(name="verification_code", length = 64)
    private String verificationCode;
    public enum Role{
        USER, ADMIN, ORG_REP
    }

    public User() {}

    public User(Integer userId, String username, String password, String email, String fullName, Role role, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
}
