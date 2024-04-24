package edu.tcu.cs.peerevaluation.team.dto;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TeamDto(Integer id,
                      @NotEmpty(message = "Team Name is required.")
                      String teamName,
                      String academicYear,
                      SectionDto section,
                      List<Integer> studentIds,
                      InstructorDto instructor) {
}
