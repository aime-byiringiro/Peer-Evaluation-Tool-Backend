package edu.tcu.cs.peerevaluation.Student;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserService;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.student.StudentUserCombined;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  StudentService studentService;

  @MockBean
  UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  List<Student> students;

  @BeforeEach
  void setUp() {
    this.students = new ArrayList<>();

    List<Team> team1List = new ArrayList<Team>();
    List<Team> team2List = new ArrayList<Team>();

    Team team1 = new Team();
    team1.setTeamName("team1");
    team1List.add(team1);

    Team team2 = new Team();
    team2.setTeamName("team2");
    team2List.add(team2);

    Section sec1 = new Section();
    sec1.setSectionName("section1");
    sec1.setAcademicYear("2024");
    sec1.setTeams(team1List);

    Section sec2 = new Section();
    sec2.setSectionName("section2");
    sec2.setAcademicYear("2025");
    sec2.setTeams(team2List);

    team1.setSection(sec1);
    team2.setSection(sec2);

    Student s1 = new Student();
    s1.setId(132);
    s1.setFirstName("John");
    s1.setLastName("Doe");
    Student s2 = new Student();
    s2.setId(465);
    s2.setFirstName("Jane");
    s2.setLastName("Doe");
    Student s3 = new Student();
    s3.setId(798);
    s3.setFirstName("Bob");
    s3.setLastName("Jones");
    Student s4 = new Student();
    s4.setId(1324);
    s4.setFirstName("John");
    s4.setLastName("Smith");

    s1.assignTeam(team1);
    s2.assignTeam(team1);
    s3.assignTeam(team2);
    s4.assignTeam(team2);

    students.add(s1);
    students.add(s2);
    students.add(s3);
    students.add(s4);

  }

  @AfterEach
  void tearDown() {

  }

  @Test
  void testFindStudentByIdSuccess() throws Exception {
    // Given
    given(this.studentService.findById(132)).willReturn(this.students.get(0));
    System.out.print(this.students.get(0));

    // when and then
    this.mockMvc.perform(get("/students/132").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find One Success"))
        .andExpect(jsonPath("$.data.id").value("132"))
        .andExpect(jsonPath("$.data.firstName").value("John"));
  }

  @Test
  void testFindStudentByIdNotFound() throws Exception {
    // Given
    given(this.studentService.findById(132)).willThrow(new ObjectNotFoundException("student", "132"));

    // When and Then
    this.mockMvc.perform(get("/students/132").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 132 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void testFindStudentByFirstName() throws Exception {
    // Given
    given(this.studentService.searchStudents("John", null, null, null, null))
        .willReturn(Arrays.asList(this.students.get(0), this.students.get(3)));

    // When and Then
    this.mockMvc.perform(get("/students/search?firstName=John").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[1].id").value(132))
        .andExpect(jsonPath("$.data[0].id").value(1324));
  }

  @Test
  void testFindStudentByLastName() throws Exception {
    // Given
    given(this.studentService.searchStudents(null, "Doe", null, null, null))
        .willReturn(Arrays.asList(this.students.get(0), this.students.get(1)));

    // When and Then
    this.mockMvc.perform(get("/students/search?lastName=Doe").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[0].id").value(132))
        .andExpect(jsonPath("$.data[1].id").value(465));
  }

  @Test
  void testFindStudentBySection() throws Exception {
    // Given
    given(this.studentService.searchStudents(null, null, "section1", null, null))
        .willReturn(Arrays.asList(this.students.get(0), this.students.get(1)));

    // When and Then
    this.mockMvc.perform(get("/students/search?section=section1").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[0].id").value(132))
        .andExpect(jsonPath("$.data[1].id").value(465));
  }

  @Test
  void testFindStudentByAcademicYear() throws Exception {
    // Given
    given(this.studentService.searchStudents(null, null, null, "2024", null))
        .willReturn(Arrays.asList(this.students.get(0), this.students.get(1)));

    // When and Then
    this.mockMvc.perform(get("/students/search?academicYear=2024").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[0].id").value(132))
        .andExpect(jsonPath("$.data[1].id").value(465));
  }

  @Test
  void testFindStudentByTeamName() throws Exception {
    // Given
    given(this.studentService.searchStudents(null, null, null, null, "team1"))
        .willReturn(Arrays.asList(this.students.get(0), this.students.get(1)));

    // When and Then
    this.mockMvc.perform(get("/students/search?teamName=team1").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[0].id").value(132))
        .andExpect(jsonPath("$.data[1].id").value(465));
  }

  @Test
  void testAddStudentSuccess() throws Exception {
    // Given
    StudentDto studentDto = new StudentDto(7,
        "John",
        "R",
        "Smith",
        null,
        null);

    PeerEvalUser user = new PeerEvalUser();
        user.setId(7);
        user.setUsername("Jsmith");
        user.setPassword("password");
        user.setEnabled(true);
        user.setRoles("student");

    StudentUserCombined studentUserCombined = new StudentUserCombined(studentDto, user);
    String json = this.objectMapper.writeValueAsString(studentUserCombined);

    Student savedStudent = new Student();
    savedStudent.setId(7);
    savedStudent.setFirstName("John");
    savedStudent.setMiddleInitial("R");
    savedStudent.setLastName("Smith");

    PeerEvalUser savedUser = new PeerEvalUser();
    savedUser.setUsername("Jsmith");
    savedUser.setPassword("password");
    savedUser.setEnabled(true);
    savedUser.setRoles("student");

    given(this.studentService.save(Mockito.any(Student.class))).willReturn(savedStudent);
    given(this.userService.save(Mockito.any(PeerEvalUser.class))).willReturn(savedUser);

    // When and Then
    this.mockMvc
        .perform(
            post("/students").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Add Success"))
        .andExpect(jsonPath("$.data.id").value(7))
        .andExpect(jsonPath("$.data.firstName").value("John"))
        .andExpect(jsonPath("$.data.middleInitial").value("R"))
        .andExpect(jsonPath("$.data.lastName").value("Smith"));
  }

  @Test
  void testUpdateStudentSuccess() throws Exception {
    // Given
    StudentDto studentDto = new StudentDto(1,
        "John",
        "R",
        "Smith",
        null,
        null);

    String json = this.objectMapper.writeValueAsString(studentDto);

    Student updatedStudent = new Student();
    updatedStudent.setId(1);
    updatedStudent.setFirstName("John");
    updatedStudent.setMiddleInitial("R");
    updatedStudent.setLastName("Smith");

    given(this.studentService.update(eq(1), Mockito.any(Student.class))).willReturn(updatedStudent);

    // When and Then
    this.mockMvc.perform(put("/students/1").contentType(MediaType.APPLICATION_JSON)
        .content(json).accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Update Success"))
        .andExpect(jsonPath("$.data.id").value(updatedStudent.getId()))
        .andExpect(jsonPath("$.data.firstName").value(updatedStudent.getFirstName()))
        .andExpect(jsonPath("$.data.middleInitial").value(updatedStudent.getMiddleInitial()))
        .andExpect(jsonPath("$.data.lastName").value(updatedStudent.getLastName()));
  }

  @Test
  void testUpdateStudentErrorWithNonExistentId() throws Exception {
    // Given
    StudentDto studentDto = new StudentDto(1,
        "John",
        "R",
        "Smith",
        null,
        null);

    String json = this.objectMapper.writeValueAsString(studentDto);

    given(this.studentService.update(eq(1), Mockito.any(Student.class)))
        .willThrow(new ObjectNotFoundException("student", 1));

    // When and Then
    this.mockMvc.perform(put("/students/1").contentType(MediaType.APPLICATION_JSON)
        .content(json).accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 1 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void testDeleteArtifactSuccess() throws Exception {
    // Given
    doNothing().when(this.studentService).delete(1);

    // When and Then
    this.mockMvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Delete Success"))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void testDeleteArtifactErrorWithNonExistentId() throws Exception {
    // Given
    doThrow(new ObjectNotFoundException("student", 1)).when(this.studentService)
        .delete(1);

    // When and Then
    this.mockMvc.perform(delete("/students/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 1 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

}
