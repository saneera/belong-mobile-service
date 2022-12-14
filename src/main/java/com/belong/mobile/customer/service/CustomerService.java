package com.belong.mobile.customer.service;

import com.belong.mobile.common.PaginationUtil;
import com.belong.mobile.customer.domain.Customer;
import com.belong.mobile.customer.domain.PhoneDetail;
import com.belong.mobile.customer.dto.CustomerDto;
import com.belong.mobile.customer.dto.PhoneDetailDto;
import com.belong.mobile.customer.dto.SearchCriteria;
import com.belong.mobile.customer.dto.StatusUpdateDto;
import com.belong.mobile.customer.repository.CustomerRepository;
import com.belong.mobile.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    PhoneDetailService phoneDetailService;


    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<List<CustomerDto>> getAll(SearchCriteria searchCriteria) {
        PageRequest pageRequest = PaginationUtil.buildPageRequest(
                searchCriteria.getPage(),
                searchCriteria.getSize(),
                searchCriteria.getSortBy(),
                searchCriteria.getSortDirection()
        );

        Page<Customer> customers = customerRepository.findAll(pageRequest);
        HttpHeaders httpHeader = PaginationUtil.generatePaginationHttpHeaders(customers);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(httpHeader)
                .body(
                        customers.stream()
                                .map(customer -> convertToCustomerDto(customer))
                                .collect(Collectors.toList())
                );

    }


    @Transactional
    public CustomerDto findById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found Customer with id = " + id));
        return convertToCustomerDto(customer);
    }

    @Transactional
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = customerRepository.save(convertToCustomer(customerDto));
        if (Optional.of(customerDto.getPhoneDetails()).isPresent()) {
            phoneDetailService.saveAll(customerDto.getPhoneDetails(), customer.getId());
        }
        return convertToCustomerDto(customer);
    }

    @Transactional
    public void deleteById(long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found Customer with id = " + id));
        BeanUtils.copyProperties(customerDto, customer, "id", "version");
        customerRepository.save(customer);
        phoneDetailService.replacePhoneDetails(id, customerDto.getPhoneDetails());
        return convertToCustomerDto(customer);
    }

    public PhoneDetailDto updatePhoneStatus(Long customerId, Long phoneId, StatusUpdateDto status) {
        return phoneDetailService.updatePhoneStatus(customerId, phoneId, status);
    }


    private CustomerDto convertToCustomerDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        List<PhoneDetail> phoneDetailsList = phoneDetailService.findByCustomerId(customer.getId());
        if (phoneDetailsList != null) {
            List<PhoneDetailDto> phoneDetails = phoneDetailsList.stream().map(row -> {
                return modelMapper.map(row, PhoneDetailDto.class);
            }).collect(Collectors.toList());
            customerDto.setPhoneDetails(phoneDetails);
        } else {
            customerDto.setPhoneDetails(null);
        }
        return customerDto;
    }

    private Customer convertToCustomer(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
