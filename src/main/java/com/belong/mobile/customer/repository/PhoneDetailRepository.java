package com.belong.mobile.customer.repository;

import com.belong.mobile.customer.domain.PhoneDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneDetailRepository extends JpaRepository<PhoneDetail, Long>, JpaSpecificationExecutor<PhoneDetail> {

    Optional<PhoneDetail> findByIdAndCustomerId(Long id, Long customerId);

    @Query("select p  from PhoneDetail as p  where p.customerId = :customerId order by p.id")
    Optional<List<PhoneDetail>> findByCustomerId(Long customerId);

}
