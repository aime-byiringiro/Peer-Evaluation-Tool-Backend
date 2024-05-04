package edu.tcu.cs.peerevaluation.section.ActiveWeek.converter;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.ActiveWeek;
import edu.tcu.cs.peerevaluation.section.ActiveWeek.dto.ActiveWeekDto;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class ActiveWeekToActiveWeekDtoConverter implements Converter<ActiveWeek, ActiveWeekDto> {

    private final SectionToSectionDtoConverter sectionToSectionDtoConverter;

    public ActiveWeekToActiveWeekDtoConverter(SectionToSectionDtoConverter sectionToSectionDtoConverter) {
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
    }


    @Override
    public ActiveWeekDto convert(ActiveWeek source) {
        ActiveWeekDto activeWeekDto = new ActiveWeekDto(source.getId(),
                source.getActiveWeekName(),
                this.sectionToSectionDtoConverter.convert(source.getSection()),
                source.getActiveWeekList());
        return activeWeekDto;
    }


}
