package com.belong.mobile;

import com.belong.mobile.customer.domain.Customer;
import com.belong.mobile.customer.domain.PhoneDetail;
import com.belong.mobile.customer.domain.Status;
import com.belong.mobile.customer.dto.CustomerDto;
import com.belong.mobile.customer.dto.PhoneDetailDto;
import com.belong.mobile.customer.dto.SearchCriteria;

import java.util.Arrays;
import java.util.List;

public class TestHelper {


    public static List buildPhoneDetailsIdList() {
        return Arrays.asList(1, 2, 3);
    }

    public static PhoneDetail buildPhoneDetails(Long id, Long customerId, Status status) {
        PhoneDetail phoneDetail = new PhoneDetail();
        phoneDetail.setPhoneNumber("0000000000");
        phoneDetail.setId(id);
        phoneDetail.setCustomerId(customerId);
        phoneDetail.setStatus(status);
       return phoneDetail;
    }

    public static PhoneDetailDto buildPhoneDetailDto(Long id, Long customerId, Status status) {
        PhoneDetailDto phoneDetail = new PhoneDetailDto();
        phoneDetail.setPhoneNumber("0000000000");
        phoneDetail.setId(id);
        phoneDetail.setCustomerId(customerId);
        phoneDetail.setStatus(status);
        return phoneDetail;
    }

    public static SearchCriteria buildSearchCriteria() {
        SearchCriteria sc = new SearchCriteria();
        sc.setPage(0);
        sc.setSize(10);
        return sc;
    }

    public static Customer buildCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Wick");
        customer.setId(1L);
        customer.setPhoneDetailIds(buildPhoneDetailsIdList());
        return customer;
    }

    public static CustomerDto buildCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName("John");
        customerDto.setLastName("Wick");
        customerDto.setId(1);
        customerDto.setPhoneDetails(Arrays.asList(buildPhoneDetailDto(1L, 1L, Status.Active)));
        return customerDto;
    }
}
