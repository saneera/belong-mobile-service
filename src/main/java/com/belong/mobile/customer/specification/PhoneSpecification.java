package com.belong.mobile.customer.specification;

import com.belong.mobile.customer.domain.PhoneDetail;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PhoneSpecification implements Specification<PhoneDetail> {
    private PhoneDetail filter;

    public PhoneSpecification(PhoneDetail filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<PhoneDetail> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getCustomerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("customerId"), filter.getCustomerId()));
        }
        return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
    }
}
