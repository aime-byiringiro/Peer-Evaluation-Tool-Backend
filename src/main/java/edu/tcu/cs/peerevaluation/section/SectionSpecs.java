package edu.tcu.cs.peerevaluation.section;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SectionSpecs {

    public static Specification<Section> hasSectionName(String providedSectionName){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sectionName"), providedSectionName);
    }

    public static Specification<Section> hasAcademicYear(String providedAcademicYear){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("academicYear")), "%" + providedAcademicYear.toLowerCase()+ "%");
    }


}
