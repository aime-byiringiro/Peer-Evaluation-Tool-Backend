package edu.tcu.cs.peerevaluation.section;

import ch.qos.logback.core.model.INamedModel;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


import java.sql.Statement;
import java.util.Comparator;
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

    @GetMapping("/section_search")
    public Result sectionSearch(
            @RequestParam(value = "sectionName", required = false) String sectionName,
            @RequestParam(value = "academicYear", required = false) String academicYear
    ) {
        List<Section> foundSections = this.sectionService.searchSections(sectionName, academicYear);
        if(foundSections.size() >1) {
            Comparator<Section> sortByAcademicYear = Comparator.comparing(Section::getAcademicYear,
                    Comparator.reverseOrder());

            Comparator<Section> sortBySectionName = Comparator.comparing(Section::getSectionName);

            Comparator<Section> sortByBoth = sortByAcademicYear.thenComparing(sortBySectionName);
            foundSections.sort(sortByBoth);
        }

        List<SectionDto> sectionDtos = foundSections.stream()
                .map(this.sectionToSectionDtoConverter::convert)
                .toList();


        return new Result(true, StatusCode.SUCCESS, "Search Success", sectionDtos);
    }



    @GetMapping("/{sectionID}")
    public Result getBySectionID(@PathVariable Integer sectionID){
        Section foundSection = this.sectionService.findBySectionId(sectionID);
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
    public Result getCurrentWeekById(@PathVariable String sectionID){
        Section foundSection = this.sectionService.viewBySectionName(sectionID);
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