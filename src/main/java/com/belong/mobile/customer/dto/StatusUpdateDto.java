package com.belong.mobile.customer.dto;

import com.belong.mobile.customer.domain.Status;

public class StatusUpdateDto {
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
