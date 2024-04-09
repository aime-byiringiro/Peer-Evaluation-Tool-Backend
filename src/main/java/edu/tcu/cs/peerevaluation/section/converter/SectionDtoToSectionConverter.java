package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;

public class SectionDtoToSectionConverter implements Converter<SectionDto, Section> {

    @Override
    public Section convert(SectionDto source) {
        Section section = new Section();
        section.setId(source.id());
        section.setAcademicYear(source.academicYear());
        section.setTeams(source.teams());
        return section;
    }
}
