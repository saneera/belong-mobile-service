package com.belong.mobile.cutomer.service;

import com.belong.mobile.cutomer.domain.PhoneDetail;
import com.belong.mobile.cutomer.dto.PhoneDetailDto;
import com.belong.mobile.cutomer.dto.StatusUpdateDto;
import com.belong.mobile.cutomer.repository.PhoneDetailRepository;
import com.belong.mobile.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<PhoneDetail> findByCustomerId(Long customerId) {
        Optional<List<PhoneDetail>> phoneDetailsList = phoneDetailRepository.findByCustomerId(customerId);
        if (phoneDetailsList.isPresent()) {
            return phoneDetailsList.get();
        } else {
            return null;
        }
    }

    public void saveAll(List<PhoneDetailDto> phoneDetails, Long customerId) {
        phoneDetailRepository.saveAll(phoneDetails.stream().map(row -> {
            PhoneDetail details = convertToPhoneDetail(row);
            details.setCustomerId(customerId);
            return details;
        }).collect(Collectors.toList()));
    }

    public void replacePhoneDetails(Long customerId, List<PhoneDetailDto> phoneDetails) {
        Optional<List<PhoneDetail>> existing = phoneDetailRepository.findByCustomerId(customerId);
        if(phoneDetails == null || (phoneDetails != null && phoneDetails.isEmpty())) {
            if(existing.isPresent() && !existing.get().isEmpty() ){
                phoneDetailRepository.deleteAll(existing.get());
            }
        } else {

            List<PhoneDetail> newPhoneDetails = phoneDetails.stream().map(phoneDetail -> {
                return convertToPhoneDetail(phoneDetail);
            }).collect(Collectors.toList());

            List<PhoneDetail> removePhoneDetails = existing.get().stream().filter(row -> newPhoneDetails.stream().anyMatch(newPhone -> newPhone.getId()!=row.getId()))
                    .collect(Collectors.toList());

            if(!removePhoneDetails.isEmpty()) {
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
