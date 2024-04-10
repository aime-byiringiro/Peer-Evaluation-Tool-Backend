package edu.tcu.cs.peerevaluation.student.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;

@Component
public class StudentDtoToStudentConverter implements Converter<StudentDto, Student> {

  @Override
  public Student convert(StudentDto source) {
    Student student = new Student();
    student.setId(source.id());
    student.setFirstName(source.firstName());
    student.setLastName(source.lastName());
    student.setEmail(source.email());
    //TODO the DTO has a teamName field, which is unique, so we can just find the team by that name
    //student.assignTeam(null);
    return student;
  }
}
