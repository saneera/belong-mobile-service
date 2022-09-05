package com.belong.mobile.cutomer.controller;

import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.SearchCriteria;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.service.PhoneDetailService;
import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/customers")
@Validated
public class PhoneDetailController {

    @Autowired
    private PhoneDetailService phoneDetailService;

    @GetMapping("/{id}/phone-details")
    @Operation(
            summary = "Get all Phone details for customer",
            description = "Returns first N Phone details specified by the size parameter with page offset specified by page parameter.")
    public ResponseEntity<List<PhoneDetailDto>> getAllPhoneNumbersForCustomer(
            @PathVariable @NotNull Long id,
            SearchCriteria searchCriteria
    ) {
        return phoneDetailService.getAll(id, searchCriteria);
    }


    @PatchMapping("/{id}/phone-details/{phoneDetailId}/status")
    @Operation(
            summary = "Activate or Inactivate Phone number for customer",
            description = "Activate or Inactivate Phone number for customer")
    public PhoneDetailDto updatePhoneStatus(
            @PathVariable @NotNull Long id,
            @PathVariable @NotNull Long phoneDetailId,
            @Valid @RequestBody StatusUpdateDto status
    ) {
        return phoneDetailService.updatePhoneStatus(id, phoneDetailId, status);
    }

}
