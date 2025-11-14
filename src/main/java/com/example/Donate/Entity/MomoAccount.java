package com.example.Donate.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "momo_accounts")
public class MomoAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer momoId;

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Organizations organization;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String accountHolder;

    // Getters and Setters
    public Integer getMomoId() {
        return momoId;
    }

    public void setMomoId(Integer momoId) {
        this.momoId = momoId;
    }

    public Organizations getOrganization() {
        return organization;
    }

    public void setOrganization(Organizations organization) {
        this.organization = organization;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}

