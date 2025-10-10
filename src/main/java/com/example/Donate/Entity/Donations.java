package com.example.Donate.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "donations")
public class Donations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Donation_Campaigns campaign;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    private Timestamp donatedAt;

    public Donations() {}

    public Donations(Long donationId, User user, Donation_Campaigns campaign, BigDecimal amount, Timestamp donatedAt) {
        this.donationId = donationId;
        this.user = user;
        this.campaign = campaign;
        this.amount = amount;
        this.donatedAt = donatedAt;
    }

    public Long getDonationId() { return donationId; }
    public void setDonationId(Long donationId) { this.donationId = donationId; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Donation_Campaigns getCampaign() { return campaign; }
    public void setCampaign(Donation_Campaigns campaign) { this.campaign = campaign; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Timestamp getDonatedAt() { return donatedAt; }
    public void setDonatedAt(Timestamp donatedAt) { this.donatedAt = donatedAt; }
}
