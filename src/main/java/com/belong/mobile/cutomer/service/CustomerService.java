package com.belong.mobile.cutomer.service;

import com.belong.mobile.common.PaginationUtil;
import com.belong.mobile.cutomer.domain.Customer;
import com.belong.mobile.cutomer.domain.PhoneDetail;
import com.belong.mobile.cutomer.dto.CustomerDto;
import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.SearchCriteria;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.repository.CustomerRepository;
import com.belong.mobile.cutomer.repository.PhoneDetailRepository;
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
    private PhoneDetailRepository phoneDetailRepository;

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
                .status(HttpStatus.OK)
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
            phoneDetailRepository.saveAll(customerDto.getPhoneDetails().stream().map(row -> {
                PhoneDetail details = convertToPhoneDetail(row);
                details.setCustomerId(customer.getId());
                return details;
            }).collect(Collectors.toList()));
        }
        return convertToCustomerDto(customer);
    }

    @Transactional
    public void deleteById(long id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public PhoneDetailDto updatePhoneStatus(Long customerId, Long phoneId, StatusUpdateDto status) {
        PhoneDetail phoneNumber = phoneDetailRepository.findByIdAndCustomerId(phoneId, customerId)
                .orElseThrow(() -> new NotFoundException("Not found Phone with id = " + phoneId));

        phoneNumber.setStatus(status.getStatus());
        phoneNumber = phoneDetailRepository.save(phoneNumber);
        return modelMapper.map(phoneNumber, PhoneDetailDto.class);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found Customer with id = " + id));
        BeanUtils.copyProperties(customerDto, customer, "id", "version");
        customerRepository.save(customer);
        return convertToCustomerDto(customer);
    }

    private CustomerDto convertToCustomerDto(Customer customer) {
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        Optional<List<PhoneDetail>> phoneDetailsList = phoneDetailRepository.findByCustomerId(customer.getId());
        if (phoneDetailsList.isPresent()) {
            List<PhoneDetailDto> phoneDetails = phoneDetailsList.get().stream().map(row -> {
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

    private PhoneDetail convertToPhoneDetail(PhoneDetailDto phoneDetailDto) {
        return modelMapper.map(phoneDetailDto, PhoneDetail.class);
    }
}
