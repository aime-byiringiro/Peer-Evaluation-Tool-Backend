package edu.tcu.cs.peerevaluation.team.dto;

import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TeamDto(String id,
                      @NotEmpty(message = "Section is required.")
                      SectionDto section,
                      @NotEmpty(message = "Students are required.")
                      List<Student> students) {
}
