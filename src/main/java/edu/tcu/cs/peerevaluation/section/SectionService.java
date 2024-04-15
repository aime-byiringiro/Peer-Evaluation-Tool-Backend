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

    public Section adminFindsSeniorDesignSectionsById(String sectionName){
        return this.sectionRepository
                .findById(Integer.valueOf(sectionName))
                .orElseThrow( () -> new SectionNotFoundException(sectionName));
    }
    public Section adminViewSeniorDesignSectionsById(String Id){
        return null;
    }


    public void adminCreatesSeniorDesignSections(){

    }

    public void adminEditsSeniorDesignSectionsById(){}
}
