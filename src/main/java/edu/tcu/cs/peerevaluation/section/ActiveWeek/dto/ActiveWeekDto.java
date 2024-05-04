package edu.tcu.cs.peerevaluation.section.ActiveWeek.dto;

import edu.tcu.cs.peerevaluation.section.dto.SectionDto;

import java.util.Date;
import java.util.List;

public record ActiveWeekDto(Integer id,
                            String activeWeekName,

                            SectionDto sectionDto,

                            List<Date>activeWeekList

                            ) {
}
