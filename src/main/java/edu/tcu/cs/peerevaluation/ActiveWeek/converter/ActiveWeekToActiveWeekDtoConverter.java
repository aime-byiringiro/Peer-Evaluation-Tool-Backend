package edu.tcu.cs.peerevaluation.ActiveWeek.converter;

import edu.tcu.cs.peerevaluation.ActiveWeek.ActiveWeek;
import edu.tcu.cs.peerevaluation.ActiveWeek.dto.ActiveWeekDto;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.converter.WeekToWeekDtoConverter;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.dto.WeekDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class ActiveWeekToActiveWeekDtoConverter implements Converter<ActiveWeek, ActiveWeekDto> {

    private final WeekToWeekDtoConverter weekToWeekDtoConverter;

    public ActiveWeekToActiveWeekDtoConverter(WeekToWeekDtoConverter weekToWeekDtoConverter) {
        this.weekToWeekDtoConverter = weekToWeekDtoConverter;
    }


    @Override
    public ActiveWeekDto convert(ActiveWeek source) {
        ActiveWeekDto activeWeekDto = new ActiveWeekDto(source.getId(),
                source.getActiveWeekName(),
                source.getSectionName().getSectionName(),
                weekToWeekDtoConverter(source.getWeeksList()));
        return activeWeekDto;
    }

    private List<WeekDto> weekToWeekDtoConverter(List<Week> weeksList) {
        return weeksList.stream()
                .map(this.weekToWeekDtoConverter::convert)
                .collect(Collectors.toList());
    }
}
