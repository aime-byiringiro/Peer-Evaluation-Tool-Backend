package edu.tcu.cs.peerevaluation.section;


import edu.tcu.cs.peerevaluation.section.utils.IdWorker;
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
        //newSection.setId((int)idWorker.nextId());
        return this.sectionRepository.save(newSection);
    }


    public void adminCreatesSeniorDesignSections(){

    }

    public void adminEditsSeniorDesignSectionsById(){}
}
