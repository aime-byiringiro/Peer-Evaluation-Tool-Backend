package edu.tcu.cs.peerevaluation.section;


import edu.tcu.cs.peerevaluation.section.utils.IdWorker;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public Section viewBySectionName(String sectionName){
        return this.sectionRepository.findBySectionName(sectionName)
                .orElseThrow(() -> new ObjectNotFoundException("section", sectionName));
    }


    public void adminCreatesSeniorDesignSections(){

    }

    public void adminEditsSeniorDesignSectionsById(){}
}
