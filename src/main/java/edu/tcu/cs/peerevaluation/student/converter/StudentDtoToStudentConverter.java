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
    student.setMiddleInitial(source.middleInitial());
    student.setLastName(source.lastName());
    student.setEmail(source.email());
    return student;
  }
}
