package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


import java.sql.Statement;
import java.util.List;
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
    public Result findSectionByCriteria(@RequestBody Map <String, String> searchCriteria , Pageable pageable) {
        Page<Section> sectionPage = this.sectionService.findByCriteria(searchCriteria, pageable);
        Page<SectionDto> sectionDtoPage = sectionPage.map(this.sectionToSectionDtoConverter::convert);

        if (sectionPage.getContent().isEmpty()) {
            return new Result(true, StatusCode.SUCCESS, " Couldn't find this section");
        }
        else {
            return new Result(true, StatusCode.SUCCESS, "Search Success", sectionDtoPage.getContent());
        }



    }

    @GetMapping("{sectionID}")
    public Result findSectionBySectionID(@PathVariable Integer sectionID) {
        Section foundSection = this.sectionService.adminFindsSeniorDesignSectionsBySectionID(sectionID);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection); // convert the json section into section object
        return new Result(true, StatusCode.SUCCESS, "Find Success", sectionDto);

    }

    @PostMapping
    public Result createNewSection(@RequestBody SectionDto sectionDto) {
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);

    }

}