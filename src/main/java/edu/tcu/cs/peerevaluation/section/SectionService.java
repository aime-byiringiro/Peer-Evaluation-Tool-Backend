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

    public Section adminFindsSeniorDesignSectionsById(String sectionId){
        return this.sectionRepository
                .findById(sectionId)
                .orElseThrow( () -> new SectionNotFoundException(sectionId));
    }

    public Section adminViewSeniorDesignSectionsById(String Id){
        return null;
    }


    public Section adminCreatesSeniorDesignSections(){
        return null;
    }


    public Section adminEditsSeniorDesignSectionsById(){
        return null;
    }



}
