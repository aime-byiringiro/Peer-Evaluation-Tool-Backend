package edu.tcu.cs.peerevaluation.student;

import org.springframework.web.bind.annotation.RestController;

import edu.tcu.cs.peerevaluation.student.converter.StudentDtoToStudentConverter;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  private final StudentToStudentDtoConverter studentToStudentDtoConverter;

  private final StudentDtoToStudentConverter studentDtoToStudentConverter;


  public StudentController(StudentService studentService, StudentToStudentDtoConverter studentToStudentDtoConverter, StudentDtoToStudentConverter studentDtoToStudentConverter) {
    this.studentService = studentService;
    this.studentToStudentDtoConverter = studentToStudentDtoConverter;
    this.studentDtoToStudentConverter = studentDtoToStudentConverter;
  }

  @GetMapping
  public Result findAllArtifacts() {
    List<Student> foundStudents = this.studentService.findAll();
    List<StudentDto> studentDtos = foundStudents.stream()
            .map(foundStudent ->
                    this.studentToStudentDtoConverter.convert(foundStudent))
            .collect(Collectors.toList());
    return new Result(true, StatusCode.SUCCESS, "Find All Success", studentDtos);

  }
  
  


}
