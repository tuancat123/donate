package com.example.Donate.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
public class Donations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer donationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", nullable = false)
    private Donation_Campaigns campaign;

    //ko lưu database
    @Transient
    private Integer campaignId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime donatedAt;

    // Getters and Setters
    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Donation_Campaigns getCampaign() {
        return campaign;
    }

    public void setCampaign(Donation_Campaigns campaign) {
        this.campaign = campaign;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDonatedAt() {
        return donatedAt;
    }

    public void setDonatedAt(LocalDateTime donatedAt) {
        this.donatedAt = donatedAt;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }
}

