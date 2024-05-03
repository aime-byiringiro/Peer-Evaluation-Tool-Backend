package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import jakarta.persistence.criteria.CriteriaBuilder;
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
        } else {
            return new Result(true, StatusCode.SUCCESS, "Search Success", sectionDtoPage.getContent());
        }

    }
    @GetMapping("/{sectionName}")
    public Result viewSectionById(@PathVariable String sectionName){
        Section foundSection = this.sectionService.viewBySectionName(sectionName);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection);
        return new Result(true, StatusCode.SUCCESS, "View One Success", sectionDto);
    }

    @PostMapping
    public Result createNewSection(@RequestBody SectionDto sectionDto) {
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);

    }
    @PutMapping("{sectionID}")
    public Result editSection(@PathVariable Integer sectionID, @Valid @RequestBody SectionDto sectionDto){
        Section edit = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section  editedSection = this.sectionService.edit(sectionID, edit);
        SectionDto editSectionDto = this.sectionToSectionDtoConverter.convert(editedSection);
        return new Result(true, StatusCode.SUCCESS, "Edit Success", editSectionDto);
    }

    //current week method
    @GetMapping("/week/{sectionName}")
    public Result getCurrentWeekById(@PathVariable String sectionName){
        Section foundSection = this.sectionService.viewBySectionName(sectionName);
        String currentWeek = foundSection.getCurrentWeek();
        /*
         * TODO
         * once Aime implents active weeks,
         * rather than return just a week
         * return 2 things:
         * 1 - a String, the current week
         * 2 - a boolean, is it an active week
         */
        return new Result(true, StatusCode.SUCCESS, "Edit Success", currentWeek);
    }

    @GetMapping("/invite/{sectionId}")
    public Result inviteSection(@PathVariable Integer sectionId) {
        return new Result(true, StatusCode.SUCCESS, "Email sent");
    }

}