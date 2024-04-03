package edu.tcu.cs.peerevaluation.Student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentNotFoundException;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.student.StudentService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @Mock
  StudentRepository studentRepository;

  @InjectMocks
  StudentService studentService;

  @BeforeEach
  void setUp(){

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
              .isInstanceOf(StudentNotFoundException.class)
              .hasMessage("Could not find student with Id 1 :(");
    verify(this.studentRepository, times(1)).findById("1");
  }

}
