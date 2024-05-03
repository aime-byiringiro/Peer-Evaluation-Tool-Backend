package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Map;

@Service
@Transactional
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section adminFindsSeniorDesignSectionsBySectionID(Integer sectionID){
        return this.sectionRepository
                .findById(sectionID)
                .orElseThrow( () -> new SectionNotFoundException(sectionID));
    }

    public Section save(Section newSection){
        return this.sectionRepository.save(newSection);

    }

    public Page<Section> findAll(Pageable pageable){
        return this.sectionRepository.findAll(pageable);
    }

    public Page<Section> findByCriteria(Map<String, String> searchCriteria, Pageable pageable) {

        Specification<Section> spec = Specification.where(null);

        if(StringUtils.hasLength(searchCriteria.get("sectionName"))){
            spec = spec.and(SectionSpecs.hasSectionName(searchCriteria.get("sectionName")));
        }

        if (StringUtils.hasLength(searchCriteria.get("academicYear"))){
            spec    = spec.and(SectionSpecs.hasAcademicYear(searchCriteria.get("academicYear")));
        }
        return this.sectionRepository.findAll(spec, pageable); //
    }
    /*
    Tne Admin wants to change the details of an existing senior design section, so that the
    section information is correct and up-to-date
     */
    public Section edit(Integer sectionID, Section edit){
        return this.sectionRepository.findById(sectionID)
                .map(oldSection -> {
                    oldSection.setSectionName(edit.getSectionName());
                    oldSection.setAcademicYear(edit.getAcademicYear());
                    oldSection.setFirstDay(edit.getFirstDay());
                    oldSection.setLastDay(edit.getLastDay());
                    oldSection.setRubric(edit.getRubric());
                    return this.sectionRepository.save(oldSection);
                })
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionID));
    }
    public Section viewBySectionName(String sectionName){
        return this.sectionRepository.findBySectionName(sectionName)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));
    }

}
