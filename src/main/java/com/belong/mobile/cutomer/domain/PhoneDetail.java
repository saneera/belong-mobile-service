package com.belong.mobile.cutomer.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phone_details", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "id"}))
public class PhoneDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "customer_id")
    private Long customerId;

    @NotNull
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Column(nullable = false, name = "status")
    private Status status;


    @Version
    private Long version;


    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
