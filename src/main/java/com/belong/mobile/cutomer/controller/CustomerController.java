package com.belong.mobile.cutomer.controller;

import com.belong.mobile.cutomer.dto.CustomerDto;
import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.SearchCriteria;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.service.CustomerService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/customers")
    public CustomerDto createCustomer(@RequestBody CustomerDto customer) {
        return customerService.save(customer);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAll(
            SearchCriteria searchCriteria
    ) {
        return customerService.getAll(searchCriteria);
    }

    @GetMapping("/customers/{id}")
    public CustomerDto findById(@PathVariable @NotNull Long id) {
        return customerService.findById(id);
    }


    @PutMapping("/customers/{id}")
    public CustomerDto updateCustomer(
            @PathVariable @NotNull Long id,
            @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }


    @DeleteMapping("/customers/{id}")
    public void deleteById(@PathVariable("id") long id) {
        customerService.deleteById(id);
    }


    @PatchMapping("/customers/{id}/phone-numbers/{phoneId}/status")
    public PhoneDetailDto updatePhoneStatus(
            @PathVariable @NotNull Long id,
            @PathVariable @NotNull Long phoneId,
            @Valid @RequestBody StatusUpdateDto status
    ) {
        return customerService.updatePhoneStatus(id, phoneId, status);
    }

}
