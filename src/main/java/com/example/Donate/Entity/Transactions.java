package com.example.Donate.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "donation_id")
    private Donations donation;

    @Column(name = "transaction_code", length = 100, unique = true)
    private String transactionCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;

    private Timestamp createdAt;

    public enum PaymentMethod {
        VNPAY, MOMO, BANK_TRANSFER
    }

    public enum Status {
        SUCCESS, FAILED, PENDING
    }

    public Transactions() {}

    public Transactions(Long transactionId, Donations donation, String transactionCode,
                       PaymentMethod paymentMethod, Status status, Timestamp createdAt) {
        this.transactionId = transactionId;
        this.donation = donation;
        this.transactionCode = transactionCode;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }

    public Donations getDonation() { return donation; }
    public void setDonation(Donations donation) { this.donation = donation; }

    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
