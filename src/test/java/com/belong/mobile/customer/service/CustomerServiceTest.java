package com.belong.mobile.customer.service;

import com.belong.mobile.common.PaginationUtil;
import com.belong.mobile.customer.domain.Customer;
import com.belong.mobile.customer.dto.CustomerDto;
import com.belong.mobile.customer.dto.SearchCriteria;
import com.belong.mobile.customer.repository.CustomerRepository;
import com.belong.mobile.customer.repository.PhoneDetailRepository;
import cucumber.api.java.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static com.belong.mobile.TestHelper.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    PhoneDetailService phoneDetailService;

    @Mock
    PhoneDetailRepository phoneDetailRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @DisplayName("Get All Customer Test")
    @Test
    public void getCustomerList() {
        SearchCriteria searchCriteria = buildSearchCriteria();
        PageRequest pageRequest = PaginationUtil.buildPageRequest(
                searchCriteria.getPage(),
                searchCriteria.getSize(),
                searchCriteria.getSortBy(),
                searchCriteria.getSortDirection()
        );

        Page<Customer> pro = Mockito.mock(Page.class);
        Mockito.when(customerRepository.findAll(pageRequest)).thenReturn(pro);
        //test
        customerService.getAll(searchCriteria);
        verify(customerRepository, times(1)).findAll(pageRequest);
    }

    @DisplayName("Get Customer By Id Test")
    @Test
    public void getCustomerById() {
        Customer customerOne = buildCustomer();
        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customerOne));
        Mockito.when(phoneDetailService.findByCustomerId(1L)).thenReturn(null);
        when(modelMapper.map(customerOne, CustomerDto.class)).thenReturn(buildCustomerDto());
        //test
        customerService.findById(1L);
        verify(customerRepository, times(1)).findById(1L);
        verify(phoneDetailService, times(1)).findByCustomerId(1L);
    }




}
