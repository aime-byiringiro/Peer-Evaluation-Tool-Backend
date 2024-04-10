package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionToSectionDtoConverter implements Converter<Section, SectionDto> {
    @Override
    public SectionDto convert(Section source) {
        SectionDto sectionDto = new SectionDto(source.getId(),
                source.getAcademicYear(),
                source.getTeams());

        return sectionDto;
    }
}
