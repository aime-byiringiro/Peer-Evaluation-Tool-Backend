package edu.tcu.cs.peerevaluation.ActiveWeek.dto;

import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.dto.WeekDto;

import java.util.List;

public record ActiveWeekDto(Integer id,
                            String ActiveWeekName,

                            String SectionName,

                            List<WeekDto> weekDtoList) {
}
