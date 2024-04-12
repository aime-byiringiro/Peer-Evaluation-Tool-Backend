package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Statement;


@RestController
@RequestMapping("/section")
public class SectionController {
    private final SectionService sectionService;
    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    private final SectionToSectionDtoConverter  sectionToSectionDtoConverter;

    public SectionController(SectionService sectionService, SectionDtoToSectionConverter sectionDtoToSectionConverter, SectionToSectionDtoConverter sectionToSectionDtoConverter) {
        this.sectionService = sectionService;
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }


    @GetMapping("/{sectionId") 
    public Result findSectionById(@PathVariable String sectionId){
        Section foundSection = this.sectionService.adminFindsSeniorDesignSectionsById(sectionId);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return  new Result(true, StatusCode.SUCCESS, "Find One Success", sectionDto);


    }

}
