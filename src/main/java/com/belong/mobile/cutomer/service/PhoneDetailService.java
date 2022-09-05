package com.belong.mobile.cutomer.service;

import com.belong.mobile.common.PaginationUtil;
import com.belong.mobile.cutomer.domain.PhoneDetail;
import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.SearchCriteria;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.repository.PhoneDetailRepository;
import com.belong.mobile.cutomer.specification.PhoneSpecification;
import com.belong.mobile.exception.NotFoundException;
import org.modelmapper.ModelMapper;
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
public class PhoneDetailService {


    @Autowired
    private PhoneDetailRepository phoneDetailRepository;

    @Autowired
    private ModelMapper modelMapper;


    public ResponseEntity<List<PhoneDetailDto>> getAll(Long id, SearchCriteria searchCriteria) {
        PageRequest pageRequest = PaginationUtil.buildPageRequest(
                searchCriteria.getPage(),
                searchCriteria.getSize(),
                searchCriteria.getSortBy(),
                searchCriteria.getSortDirection()
        );
        PhoneDetail pd = new PhoneDetail();
        pd.setCustomerId(id);

        PhoneSpecification phoneSpecification = new PhoneSpecification(pd);

        Page<PhoneDetail> phoneDetails = phoneDetailRepository.findAll(phoneSpecification, pageRequest);
        HttpHeaders httpHeader = PaginationUtil.generatePaginationHttpHeaders(phoneDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(httpHeader)
                .body(
                        phoneDetails.stream()
                                .map(customer -> modelMapper.map(customer, PhoneDetailDto.class))
                                .collect(Collectors.toList())
                );
    }


    public List<PhoneDetail> findByCustomerId(Long customerId) {
        Optional<List<PhoneDetail>> phoneDetailsList = phoneDetailRepository.findByCustomerId(customerId);
        if (phoneDetailsList.isPresent()) {
            return phoneDetailsList.get();
        } else {
            return null;
        }
    }

    @Transactional
    public void saveAll(List<PhoneDetailDto> phoneDetails, Long customerId) {
        phoneDetailRepository.saveAll(phoneDetails.stream().map(row -> {
            PhoneDetail details = convertToPhoneDetail(row);
            details.setCustomerId(customerId);
            return details;
        }).collect(Collectors.toList()));
    }

    @Transactional
    public void replacePhoneDetails(Long customerId, List<PhoneDetailDto> phoneDetails) {
        Optional<List<PhoneDetail>> existing = phoneDetailRepository.findByCustomerId(customerId);
        if (phoneDetails == null || (phoneDetails != null && phoneDetails.isEmpty())) {
            if (existing.isPresent() && !existing.get().isEmpty()) {
                phoneDetailRepository.deleteAll(existing.get());
            }
        } else {

            List<PhoneDetail> newPhoneDetails = phoneDetails.stream().map(phoneDetail -> {
                return convertToPhoneDetail(phoneDetail);
            }).collect(Collectors.toList());

            List<PhoneDetail> removePhoneDetails = existing.get().stream().filter(row -> newPhoneDetails.stream().anyMatch(newPhone -> newPhone.getId() != row.getId()))
                    .collect(Collectors.toList());

            if (!removePhoneDetails.isEmpty()) {
                phoneDetailRepository.deleteAll(removePhoneDetails);
                phoneDetailRepository.flush();
            }
            phoneDetailRepository.saveAll(newPhoneDetails);
        }
    }


    @Transactional
    public PhoneDetailDto updatePhoneStatus(Long customerId, Long phoneId, StatusUpdateDto status) {
        PhoneDetail phoneDetail = phoneDetailRepository.findByIdAndCustomerId(phoneId, customerId)
                .orElseThrow(() -> new NotFoundException("Not found Phone with id = " + phoneId));

        phoneDetail.setStatus(status.getStatus());
        phoneDetail = phoneDetailRepository.save(phoneDetail);
        return modelMapper.map(phoneDetail, PhoneDetailDto.class);
    }


    private PhoneDetail convertToPhoneDetail(PhoneDetailDto phoneDetailDto) {
        return modelMapper.map(phoneDetailDto, PhoneDetail.class);
    }


}
