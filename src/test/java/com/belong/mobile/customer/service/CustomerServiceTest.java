package com.belong.mobile.customer.service;

import com.belong.mobile.common.PaginationUtil;
import com.belong.mobile.cutomer.domain.Customer;
import com.belong.mobile.cutomer.domain.PhoneDetail;
import com.belong.mobile.cutomer.domain.Status;
import com.belong.mobile.cutomer.dto.CustomerDto;
import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.SearchCriteria;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.repository.CustomerRepository;
import com.belong.mobile.cutomer.repository.PhoneDetailRepository;
import com.belong.mobile.cutomer.service.CustomerService;
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
        Mockito.when(phoneDetailRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
        when(modelMapper.map(customerOne, CustomerDto.class)).thenReturn(buildCustomerDto());
        //test
        customerService.findById(1L);
        verify(customerRepository, times(1)).findById(1L);
        verify(phoneDetailRepository, times(1)).findByCustomerId(1L);
    }


    @DisplayName("Activate Customer Phone")
    @Test
    public void activateCustomerPhone() {
        PhoneDetail phoneDetail = buildPhoneDetails(1L, 1L, Status.Inactive);
        Mockito.when(phoneDetailRepository.findByIdAndCustomerId(1L, 1L)).thenReturn(Optional.of(phoneDetail));
        PhoneDetail phoneDetailActive = buildPhoneDetails(1L, 1L, Status.Active);
        Mockito.when(phoneDetailRepository.save(phoneDetailActive)).thenReturn(phoneDetail);
        when(modelMapper.map(phoneDetail, PhoneDetailDto.class)).thenReturn(buildPhoneDetailDto(1L, 1L, Status.Active));
        StatusUpdateDto status =new StatusUpdateDto();
        status.setStatus(Status.Active);
        //test
        customerService.updatePhoneStatus(1L, 1L, status);
        verify(phoneDetailRepository, times(1)).findByIdAndCustomerId(1L, 1L);
    }

}
