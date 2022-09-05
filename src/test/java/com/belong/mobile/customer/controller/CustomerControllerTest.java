package com.belong.mobile.customer.controller;

import com.belong.mobile.TestHelper;
import com.belong.mobile.customer.dto.CustomerDto;
import com.belong.mobile.customer.dto.SearchCriteria;
import com.belong.mobile.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.belong.mobile.TestHelper.buildCustomerDto;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.belong.mobile.TestHelper.buildSearchCriteria;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testFindAll() throws Exception {
        CustomerDto customerDto = TestHelper.buildCustomerDto();
        SearchCriteria searchCriteria = buildSearchCriteria();
        List<CustomerDto> customers = Arrays.asList(customerDto);

        Mockito.when(customerService.getAll(searchCriteria)).thenReturn(ResponseEntity.ok(customers));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        CustomerDto customerDto = TestHelper.buildCustomerDto();
        Mockito.when(customerService.findById(1L)).thenReturn(customerDto);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDto customerDto = buildCustomerDto();
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/customers")
                        .content(asJsonString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
