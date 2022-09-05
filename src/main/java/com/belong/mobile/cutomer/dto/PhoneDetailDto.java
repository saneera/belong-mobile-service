package com.belong.mobile.cutomer.dto;

import com.belong.mobile.cutomer.domain.Status;

import javax.validation.constraints.NotEmpty;


public class PhoneDetailDto {

    private Long id;

    private Long customerId;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private Status status;

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
}
