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
import com.belong.mobile.cutomer.service.PhoneDetailService;
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
public class PhoneServiceTest {

    @InjectMocks
    PhoneDetailService phoneDetailService;


    @Mock
    PhoneDetailRepository phoneDetailRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
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
        phoneDetailService.updatePhoneStatus(1L, 1L, status);
        verify(phoneDetailRepository, times(1)).findByIdAndCustomerId(1L, 1L);
    }

}
