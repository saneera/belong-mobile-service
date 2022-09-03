package com.belong.mobile.cutomer.dto;

import java.io.Serializable;
import java.util.List;


public class CustomerDto implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private List<PhoneDetailDto> phoneDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<PhoneDetailDto> getPhoneDetails() {
        return phoneDetails;
    }

    public void setPhoneDetails(List<PhoneDetailDto> phoneDetails) {
        this.phoneDetails = phoneDetails;
    }
}
