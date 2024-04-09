package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.rubric.converter.RubricDtoToRubricConverter;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionDtoToSectionConverter implements Converter<SectionDto, Section> {

    private final RubricDtoToRubricConverter rubricDtoToRubricConverter;

    public SectionDtoToSectionConverter(RubricDtoToRubricConverter rubricDtoToRubricConverter) {
        this.rubricDtoToRubricConverter = rubricDtoToRubricConverter;
    }

    @Override
    public Section convert(SectionDto source) {
        Section section = new Section();
        section.setId(source.id());
        section.setSectionName(source.sectionName());
        section.setAcademicYear(source.academicYear());
        section.setFirstDay(source.firstDay());
        section.setLastDay(source.lastDay());
        section.setRubric(this.rubricDtoToRubricConverter.convert(source.rubricDto()));
        section.setTeams(source.teams());
        return section;
    }
}
