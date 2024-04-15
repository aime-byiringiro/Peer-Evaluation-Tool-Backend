package edu.tcu.cs.peerevaluation.section;


import edu.tcu.cs.peerevaluation.section.utils.IdWorker;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SectionService {

    private final SectionRepository sectionRepository;
    private final IdWorker idWorker;

    public SectionService(SectionRepository sectionRepository, IdWorker idWorker) {
        this.sectionRepository = sectionRepository;
        this.idWorker = idWorker;
    }


    public Section adminFindsSeniorDesignSectionsBySectionName(Integer sectionID){
        return this.sectionRepository
                .findById(sectionID)
                .orElseThrow( () -> new SectionNotFoundException(sectionID));
    }
    public Section adminViewSeniorDesignSectionsById(Integer Id){
        return null;
    }

    public Section save(Section newSection){
        newSection.setId(idWorker.nextId());
        return this.sectionRepository.save(newSection);
    }


    public void adminCreatesSeniorDesignSections(){

    }

    public void adminEditsSeniorDesignSectionsById(){}
}
