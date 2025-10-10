package com.example.Donate.Entity;

import jakarta.persistence.*;
@Entity
@Table(name = "organizations")
public class Organizations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    public Organizations() {}

    public Organizations(Long orgId, String name, String description, String contactEmail, String phone, String address) {
        this.orgId = orgId;
        this.name = name;
        this.description = description;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.address = address;
    }

    public Long getOrgId() { return orgId; }
    public void setOrgId(Long orgId) { this.orgId = orgId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
