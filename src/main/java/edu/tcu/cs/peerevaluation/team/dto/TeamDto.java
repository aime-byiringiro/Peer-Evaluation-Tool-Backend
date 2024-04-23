package edu.tcu.cs.peerevaluation.team.dto;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.dto.InstructorDto;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TeamDto(Integer id,
                      @NotEmpty(message = "Team Name is required.")
                      String teamName,
                      @NotEmpty(message = "Academic Year is required.")
                      String academicYear,
                      @NotEmpty(message = "Section is required.")
                      SectionDto section,
                      @NotEmpty(message = "Students are required.")
                      List<Integer> studentIds,
                      @NotEmpty(message = "Instructor is required.")
                      InstructorDto instructor) {
}
