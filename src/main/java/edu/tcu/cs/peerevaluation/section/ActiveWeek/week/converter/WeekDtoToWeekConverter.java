package edu.tcu.cs.peerevaluation.section.ActiveWeek.week.converter;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.section.ActiveWeek.week.dto.WeekDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WeekDtoToWeekConverter implements Converter<WeekDto, Week> {
    @Override
    public Week convert(WeekDto source) {
        Week week = new Week();
        week.setId(source.id());
        week.setWeekName(source.weekName());
        week.setStartDate(source.startDate());
        return week;
    }
}
