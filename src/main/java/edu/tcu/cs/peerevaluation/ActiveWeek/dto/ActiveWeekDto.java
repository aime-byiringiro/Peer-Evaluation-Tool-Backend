package edu.tcu.cs.peerevaluation.ActiveWeek.dto;

import edu.tcu.cs.peerevaluation.ActiveWeek.week.Week;
import edu.tcu.cs.peerevaluation.ActiveWeek.week.dto.WeekDto;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import java.util.List;

public record ActiveWeekDto(Integer id,
                            String activeWeekName,

                            SectionDto sectionDto

                            //List<WeekDto> weekDtoList
                            ) {
}
