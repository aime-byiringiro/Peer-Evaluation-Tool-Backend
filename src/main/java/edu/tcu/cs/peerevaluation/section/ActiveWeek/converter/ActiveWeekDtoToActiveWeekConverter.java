package edu.tcu.cs.peerevaluation.section.ActiveWeek.converter;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.ActiveWeek;
import edu.tcu.cs.peerevaluation.section.ActiveWeek.dto.ActiveWeekDto;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class ActiveWeekDtoToActiveWeekConverter implements Converter<ActiveWeekDto, ActiveWeek>{




    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;

    public ActiveWeekDtoToActiveWeekConverter(SectionDtoToSectionConverter sectionDtoToSectionConverter) {

        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
    }
    @Override
    public ActiveWeek convert(ActiveWeekDto source) {

        ActiveWeek activeWeek = new ActiveWeek();
        activeWeek.setId(source.id());
        activeWeek.setSection(this.sectionDtoToSectionConverter.convert(source.sectionDto()));
        activeWeek.setActiveWeekList(source.activeWeekList());
        return activeWeek;
    }

}
