package edu.tcu.cs.peerevaluation.section;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SectionService {



    private final SectionRepository sectionRepository;
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public Section adminFindsSeniorDesignSectionsBySectionName(Integer sectionID){
        return this.sectionRepository
                .findById(sectionID)
                .orElseThrow( () -> new SectionNotFoundException(sectionID));
    }
    public Section adminViewSeniorDesignSectionsById(String Id){
        return null;
    }


    public void adminCreatesSeniorDesignSections(){

    }

    public void adminEditsSeniorDesignSectionsById(){}
}
