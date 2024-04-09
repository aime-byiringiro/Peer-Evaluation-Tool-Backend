package edu.tcu.cs.peerevaluation.Student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @Mock
  StudentRepository studentRepository;

  @InjectMocks
  StudentService studentService;

  List<Student> students = new ArrayList<>();

  @BeforeEach
  void setUp(){
    Student s1 = new Student();
    s1.setId("1");
    s1.setFirstName("John");
    s1.setLastName("Doe");

    Student s2 = new Student();
    s2.setId("2");
    s2.setFirstName("Jane");
    s2.setLastName("Doe");

    Student s3 = new Student();
    s3.setId("3");
    s3.setFirstName("John");
    s3.setLastName("Smith");

    students.add(s1);
    students.add(s2);
    students.add(s3);
  }

  @AfterEach
  void tearDown(){

  }


  @Test
  void testFindStudentByIdSuccess() {
  // Given. Arragne inputs and targets. Define the behavior of Mock object artifactRepository.
  Student s = new Student();
  s.setId("132");
  s.setFirstName("John");
  s.setLastName("Doe");

  given(studentRepository.findById("132")).willReturn(Optional.of(s)); //Deines the behavior of mock object
  // When. Act on the target behavior. When steps should cover the method to be tested.
  Student returnedStudent = studentService.findById("132");
  
  // Then. Assert expected outcomes.
  assertThat(returnedStudent.getId()).isEqualTo(s.getId());
  assertThat(returnedStudent.getFirstName()).isEqualTo(s.getFirstName());
  assertThat(returnedStudent.getLastName()).isEqualTo(s.getLastName());
  verify(studentRepository, times(1)).findById("132");


  }

  @Test
  void testFindStudentByIdNotFound() {
    given(studentRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

    Throwable thrown = catchThrowable(() ->{
      @SuppressWarnings("unused")
      Student returnedStudent = studentService.findById("1");
    });

    assertThat(thrown)
              .isInstanceOf(ObjectNotFoundException.class)
              .hasMessage("Could not find student with Id 1 :(");
    verify(this.studentRepository, times(1)).findById("1");
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindStudentByFirstNameSuccess(){
      // Given

      List<Student> expectedStudents = students.stream()
                          .filter(s -> "John".equals(s.getFirstName()))
                          .collect(Collectors.toList());
  
      given(studentRepository.findAll((Specification<Student>) any())).willReturn(expectedStudents);

      // When
      List<Student> result = studentService.searchStudents("John", null, null, null, null);

      // Then
      assertThat(result).hasSize(expectedStudents.size());
      assertThat(result.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());
      assertThat(result.get(0).getLastName()).isEqualTo(students.get(0).getLastName());
      verify(studentRepository, times(1)).findAll((Specification<Student>) any());

  }
}
