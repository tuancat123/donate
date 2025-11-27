package com.example.Donate.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne  //(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "campaign_id", nullable = true)
    private Donation_Campaigns campaign;

    private String message;

    private boolean seen = false;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // ======= GETTERS & SETTERS =======

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

