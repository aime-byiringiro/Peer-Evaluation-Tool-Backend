package edu.tcu.cs.peerevaluation.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

//    Student s1 = new Student();
//    s1.setId("1");
//    s1.setFirstName("John");
//    s1.setLastName("Doe");
//
//    Student s2 = new Student();
//    s2.setId("2");
//    s2.setFirstName("Jane");
//    s2.setLastName("Doe");
//
//    Student s3 = new Student();
//    s3.setId("3");
//    s3.setFirstName("John");
//    s3.setLastName("Smith");
//
//    students.add(s1);
//    students.add(s2);
//    students.add(s3);

    List<Team> team1List = new ArrayList<Team>();
    List<Team> team2List = new ArrayList<Team>();

    Team team1 = new Team();
      team1.setTeamName("team1");
      team1List.add(team1);

    Team team2 = new Team();
      team2.setTeamName("team2");
      team2List.add(team2);

    Section sec1 = new Section();
      sec1.setSectionName("section 1");
      sec1.setAcademicYear("2024");
      sec1.setTeams(team1List);
      
    
    Section sec2 = new Section();
      sec2.setSectionName("section 2");
      sec2.setAcademicYear("2025");
      sec2.setTeams(team2List);

      team1.setSection(sec1);
      team2.setSection(sec2);
    
    Student s1 = new Student();
    s1.setId(1);
    s1.setFirstName("John");
    s1.setLastName("Doe");

    Student s2 = new Student();
    s2.setId(2);
    s2.setFirstName("Jane");
    s2.setLastName("Doe");

    Student s3 = new Student();
    s3.setId(3);
    s3.setFirstName("John");
    s3.setLastName("Smith");

    s1.assignTeam(team1);
    s2.assignTeam(team1);
    s3.assignTeam(team2);

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
  s.setId(132);
  s.setFirstName("John");
  s.setLastName("Doe");

  given(studentRepository.findById(132)).willReturn(Optional.of(s)); //Deines the behavior of mock object
  // When. Act on the target behavior. When steps should cover the method to be tested.
  Student returnedStudent = studentService.findById(132);
  
  // Then. Assert expected outcomes.
  assertThat(returnedStudent.getId()).isEqualTo(s.getId());
  assertThat(returnedStudent.getFirstName()).isEqualTo(s.getFirstName());
  assertThat(returnedStudent.getLastName()).isEqualTo(s.getLastName());
  verify(studentRepository, times(1)).findById(132);

  }

  @Test
  void testFindStudentByIdNotFound() {
    given(studentRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

    Throwable thrown = catchThrowable(() ->{
      @SuppressWarnings("unused")
      Student returnedStudent = studentService.findById(1);
    });

    assertThat(thrown)
              .isInstanceOf(ObjectNotFoundException.class)
              .hasMessage("Could not find student with Id 1 :(");
    verify(this.studentRepository, times(1)).findById(1);
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

  @SuppressWarnings("unchecked")
  @Test
  void testFindStudentBySectionSuccess(){
      // Given

      List<Student> expectedStudents = students.stream()
                          .filter(s -> "section 1".equals(s.getTeam().getSection().getSectionName()))
                          .collect(Collectors.toList());
  
      given(studentRepository.findAll((Specification<Student>) any())).willReturn(expectedStudents);

      // When
      List<Student> result = studentService.searchStudents(null, null, "section 1", null, null);

      // Then
      assertThat(result).hasSize(expectedStudents.size());
      assertThat(result.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());
      assertThat(result.get(0).getLastName()).isEqualTo(students.get(0).getLastName());
      verify(studentRepository, times(1)).findAll((Specification<Student>) any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindStudentByAcademicYearSuccess(){
      // Given

      List<Student> expectedStudents = students.stream()
                          .filter(s -> "2024".equals(s.getTeam().getSection().getAcademicYear()))
                          .collect(Collectors.toList());
  
      given(studentRepository.findAll((Specification<Student>) any())).willReturn(expectedStudents);

      // When
      List<Student> result = studentService.searchStudents(null, null, null, "2024", null);

      // Then
      assertThat(result).hasSize(expectedStudents.size());
      assertThat(result.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());
      assertThat(result.get(0).getLastName()).isEqualTo(students.get(0).getLastName());
      verify(studentRepository, times(1)).findAll((Specification<Student>) any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindStudentByTeamNameSuccess(){
      // Given

      List<Student> expectedStudents = students.stream()
                          .filter(s -> "team1".equals(s.getTeam().getTeamName()))
                          .collect(Collectors.toList());
  
      given(studentRepository.findAll((Specification<Student>) any())).willReturn(expectedStudents);

      // When
      List<Student> result = studentService.searchStudents(null, null, null, null, "team1");

      // Then
      assertThat(result).hasSize(expectedStudents.size());
      assertThat(result.get(0).getFirstName()).isEqualTo(students.get(0).getFirstName());
      assertThat(result.get(0).getLastName()).isEqualTo(students.get(0).getLastName());
      verify(studentRepository, times(1)).findAll((Specification<Student>) any());
  }

  @Test
  void testSaveSuccess() {
    // Given
    Student newStudent = new Student();
    newStudent.setFirstName("Joe");
    newStudent.setLastName("Shmoe");
    newStudent.setMiddleInitial("N");
    newStudent.setId(123456);

    given(this.studentRepository.save(newStudent)).willReturn(newStudent);

    // When
    Student savedStudent = this.studentService.save(newStudent);

    // Then
    assertThat(savedStudent.getId()).isEqualTo(123456);
    assertThat(savedStudent.getFirstName()).isEqualTo(newStudent.getFirstName());
    assertThat(savedStudent.getLastName()).isEqualTo(newStudent.getLastName());
    assertThat(savedStudent.getMiddleInitial()).isEqualTo(newStudent.getMiddleInitial());
    verify(this.studentRepository, times(1)).save(newStudent);

  }

  @Test
  void testUpdateSuccess() {
    Student oldStudent = new Student();
    oldStudent.setFirstName("Joe");
    oldStudent.setLastName("Shmoe");
    oldStudent.setMiddleInitial("N");
    oldStudent.setId(123456);

    Student update = new Student();
    update.setFirstName("Mike");
    update.setLastName("Shmoe");
    update.setMiddleInitial("N");
    update.setId(123456);

    given(this.studentRepository.findById(123456)).willReturn(Optional.of(oldStudent));
    given(this.studentRepository.save(oldStudent)).willReturn(oldStudent);
    
    //when 
    Student updatedStudent = this.studentService.update(123456, update);

    //then
    assertThat(updatedStudent.getId()).isEqualTo(123456);
    assertThat(updatedStudent.getFirstName()).isEqualTo(update.getFirstName());
    verify(this.studentRepository, times(1)).findById(123456);
    verify(this.studentRepository, times(1)).save(oldStudent);
  }

  @Test
  void testUpdateNotFound() {
    //Given
    Student update = new Student();
    update.setFirstName("Mike");
    update.setLastName("Shmoe");
    update.setMiddleInitial("N");

    given(this.studentRepository.findById(123456)).willReturn(Optional.empty());

    //When
    assertThrows(ObjectNotFoundException.class, () -> {
      this.studentService.update(123456,update);
    });

    //Then
    verify(this.studentRepository, times(1)).findById(123456);
  }

  @Test
  void testDeleteSuccess() {
    //Given
    Student student = new Student();
      student.setFirstName("Joe");
      student.setLastName("Shmoe");
      student.setMiddleInitial("N");
      student.setId(123456);
    
      given(this.studentRepository.findById(123456)).willReturn(Optional.of(student));
      doNothing().when(this.studentRepository).deleteById(123456);

      //when
      this.studentService.delete(123456);

      //then
      verify(this.studentRepository, times(1)).deleteById(123456);

  }

  @Test
  void testDeleteNotFound() {
    //Given
    given(this.studentRepository.findById(123456)).willReturn(Optional.empty());

    //When
    assertThrows(ObjectNotFoundException.class, () -> {
      this.studentService.delete(123456);
    });

    //then
    verify(this.studentRepository, times(1)).findById(123456);

  }
}

