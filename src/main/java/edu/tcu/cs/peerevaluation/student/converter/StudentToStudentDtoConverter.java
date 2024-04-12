package edu.tcu.cs.peerevaluation.student.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;

@Component
public class StudentToStudentDtoConverter implements Converter<Student, StudentDto> {

  @Override
  public StudentDto convert(Student source) {
    StudentDto studentDto = new StudentDto(source.getId(),
                                           source.getFirstName(),
                                           source.getMiddleInitial(),
                                           source.getLastName(),
                                           source.getEmail(),
                                           source.getTeam() != null
                                                    ? source.getTeam().getTeamName()
                                                    : null );
    return studentDto;
    
  }

}
