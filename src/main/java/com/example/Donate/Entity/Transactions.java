package com.example.Donate.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donations donation;

    @Column(length = 100, unique = true)
    private String transactionCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status = Status.SUCCESS;

    @ManyToOne
    @JoinColumn(name = "receiver_bank_id", nullable = true)
    private BankAccount receiverBank;

    @ManyToOne
    @JoinColumn(name = "receiver_momo_id", nullable = true)
    private MomoAccount receiverMomo;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public enum PaymentMethod {
        BANK, MOMO, PAYPAL, VNPAY
    }

    public enum Status {
        PENDING, SUCCESS, FAILED
    }

    // Getters and Setters
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Donations getDonation() {
        return donation;
    }

    public void setDonation(Donations donation) {
        this.donation = donation;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BankAccount getReceiverBank() {
        return receiverBank;
    }

    public void setReceiverBank(BankAccount receiverBank) {
        this.receiverBank = receiverBank;
    }

    public MomoAccount getReceiverMomo() {
        return receiverMomo;
    }

    public void setReceiverMomo(MomoAccount receiverMomo) {
        this.receiverMomo = receiverMomo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

