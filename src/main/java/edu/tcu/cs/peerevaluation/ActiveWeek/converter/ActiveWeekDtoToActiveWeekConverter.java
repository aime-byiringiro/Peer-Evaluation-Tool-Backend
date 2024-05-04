package edu.tcu.cs.peerevaluation.ActiveWeek.converter;

import edu.tcu.cs.peerevaluation.ActiveWeek.ActiveWeek;
import edu.tcu.cs.peerevaluation.ActiveWeek.dto.ActiveWeekDto;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.converter.WeekDtoToWeekConverter;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.dto.WeekDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

@Component
public class ActiveWeekDtoToActiveWeekConverter implements Converter<ActiveWeekDto, ActiveWeek>{

    private final WeekDtoToWeekConverter weekDtoToWeekConverter;

    public ActiveWeekDtoToActiveWeekConverter(WeekDtoToWeekConverter weekDtoToWeekConverter) {
        this.weekDtoToWeekConverter = weekDtoToWeekConverter;
    }
    @Override
    public ActiveWeek convert(ActiveWeekDto source) {

        ActiveWeek activeWeek = new ActiveWeek();
        activeWeek.setId(source.id());
        activeWeek.setActiveWeekName(source.ActiveWeekName());
        activeWeek.setWeeksList(weekConverter(source.weekDtoList()));
        return activeWeek;
    }
    private List<Week> weekConverter(List<WeekDto> weekDtos) {
        return weekDtos.stream()
                .map(this.weekDtoToWeekConverter::convert)
                .collect(Collectors.toList());
    }
}
