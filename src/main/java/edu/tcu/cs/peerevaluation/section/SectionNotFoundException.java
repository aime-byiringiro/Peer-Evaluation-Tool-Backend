package edu.tcu.cs.peerevaluation.section;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public class SectionNotFoundException extends RuntimeException {

    public SectionNotFoundException(Integer    sectionID) {
        super("Could not find section with ID " + sectionID + ":(");
    }



}
