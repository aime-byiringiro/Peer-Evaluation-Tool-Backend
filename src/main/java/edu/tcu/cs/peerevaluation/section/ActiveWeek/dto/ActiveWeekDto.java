package edu.tcu.cs.peerevaluation.section.ActiveWeek.dto;

import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

public record ActiveWeekDto(Integer id,
                            String activeWeekName,

                            SectionDto sectionDto

                            //List<WeekDto> weekDtoList
                            ) {
}
