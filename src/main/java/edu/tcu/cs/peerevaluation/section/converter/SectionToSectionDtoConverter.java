package edu.tcu.cs.peerevaluation.section.converter;

import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SectionToSectionDtoConverter implements Converter<Section, SectionDto> {

    private final RubricToRubricDtoConverter rubricToRubricDtoConverter;

    public SectionToSectionDtoConverter(RubricToRubricDtoConverter rubricToRubricDtoConverter) {
        this.rubricToRubricDtoConverter = rubricToRubricDtoConverter;
    }

    @Override
    public SectionDto convert(Section source) {
        SectionDto sectionDto = new SectionDto(source.getId(),
                                               source.getSectionName(),
                                               source.getAcademicYear(),
                                               source.getFirstDay(),
                                               source.getLastDay(),
                                               source.getRubric() != null
                                                       ? this.rubricToRubricDtoConverter.convert(source.getRubric())
                                                       :null
                                               );

        return sectionDto;
    }
}
