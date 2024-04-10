package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Statement;

public class SectionController {

    private final SectionService sectionService;
    public SectionController(SectionService sectionService) {

        this.sectionService = sectionService;
    }
    @GetMapping("#/{sectionId") //TBD
    public Result findSectionById(@PathVariable String sectionId){
        Section foundSection = this.sectionService.adminFindsSeniorDesignSectionsById(sectionId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", foundSection);
    }


}
