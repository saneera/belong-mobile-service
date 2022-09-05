package com.belong.mobile.customer.controller;

import com.belong.mobile.customer.dto.CustomerDto;
import com.belong.mobile.customer.dto.SearchCriteria;
import com.belong.mobile.customer.service.CustomerService;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/customers")
    @Operation(
            summary = "Create Cstomer",
            description = "Returns the saved Customer ")
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customer) {
        return customerService.save(customer);
    }

    @GetMapping("/customers")
    @Operation(
            summary = "Get all customers",
            description = "Returns first N customers specified by the size parameter with page offset specified by page parameter.")
    public ResponseEntity<List<CustomerDto>> getAll(
            SearchCriteria searchCriteria
    ) {
        return customerService.getAll(searchCriteria);
    }

    @GetMapping("/customers/{id}")
    @Operation(
            summary = "Get customer by id",
            description = "Returns customer for id specified.")
    public CustomerDto findById(@PathVariable @NotNull Long id) {
        return customerService.findById(id);
    }


    @PutMapping("/customers/{id}")
    @Operation(
            summary = "Update existing customer",
            description = "Updates existing customer. Returns updated customer")
    public CustomerDto updateCustomer(
            @PathVariable @NotNull Long id,
            @RequestBody CustomerDto customerDto) {
        return customerService.updateCustomer(id, customerDto);
    }

    @DeleteMapping("/customers/{id}")
    @Operation(
            summary = "Delete existing customer by id",
            description = "Delete existing customer by id provided")
    public void deleteById(@PathVariable("id") long id) {
        customerService.deleteById(id);
    }


}
