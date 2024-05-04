package edu.tcu.cs.peerevaluation.ActiveWeek.week.converter;


import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.dto.WeekDto;
import org.springframework.stereotype.Component;

@Component
public class WeekToWeekDtoConverter {

    public WeekDto convert(Week source) {
        WeekDto weekDto = new WeekDto(source.getId(),
                source.getWeekName(),
                source.getStartDate());
        return weekDto;
    }
}
