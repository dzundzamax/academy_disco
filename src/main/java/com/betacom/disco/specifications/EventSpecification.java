package com.betacom.disco.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.betacom.disco.dtos.EventFilter;
import com.betacom.disco.entities.Event;

import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventSpecification {
    public static Specification<Event> fromFilter(EventFilter filter) {
        return (root, query, CriteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getAvailable() != null) {
                if (filter.getAvailable()) {
                    Predicate isNotPast = CriteriaBuilder
                            .greaterThanOrEqualTo(root.get("date"), LocalDate.now());
                    predicates.add(CriteriaBuilder.and(isNotPast));
                } else {
                    Predicate isPast = CriteriaBuilder.lessThan(root.get("date"), LocalDate.now());
                    predicates.add(CriteriaBuilder.and(isPast));
                }

            }

            if(filter.getMaxprice() != null && filter.getMaxprice() > 0) {
                Predicate isGreaterThanMaxPrice = CriteriaBuilder.lessThanOrEqualTo(root.get("basePrice"), filter.getMaxprice());

                predicates.add(CriteriaBuilder.and(isGreaterThanMaxPrice));
            }

            return CriteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}