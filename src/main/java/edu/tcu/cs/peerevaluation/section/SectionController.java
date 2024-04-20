package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.sql.Statement;
import java.util.Map;


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
    @PostMapping("/section_search")
    public Result findSectionByCriteria(@RequestBody Map <String, String> searchCriteria , Pageable pageable){
      Page<Section> sectionPage = this.sectionService.findByCriteria(searchCriteria, pageable);
      Page<SectionDto> sectionDtoPage = sectionPage.map(this.sectionToSectionDtoConverter::convert);
      return new Result(true, StatusCode.SUCCESS, "Search Success", sectionDtoPage);
    }

}