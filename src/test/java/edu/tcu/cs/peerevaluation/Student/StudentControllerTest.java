package edu.tcu.cs.peerevaluation.Student;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  StudentService studentService;

  @Autowired
  ObjectMapper objectMapper;

  List<Student> students;

  @BeforeEach
  void setUp(){
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
  void tearDown(){

  }

 @Test
  void testFindStudentByIdSuccess() throws Exception{
    //Given
    given(this.studentService.findById(132)).willReturn(this.students.get(0));
    System.out.print(this.students.get(0));

    //when and then
    this.mockMvc.perform(get("/students/132").accept(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.flag").value(true))
            .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
            .andExpect(jsonPath("$.message").value("Find One Success"))
            .andExpect(jsonPath("$.data.id").value("132"))
            .andExpect(jsonPath("$.data.firstName").value("John"));
  }

  @Test
  void testFindStudentByIdNotFound() throws Exception {
    //Given
    given(this.studentService.findById(132)).willThrow(new ObjectNotFoundException("student","132"));

    //When and Then
    this.mockMvc.perform(get("/students/132").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.flag").value(false))
            .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
            .andExpect(jsonPath("$.message").value("Could not find student with Id 132 :("))
            .andExpect(jsonPath("$.data").isEmpty());
  } 

  @Test
  void testFindStudentByFirstName() throws Exception {
    //Given
    given(this.studentService.searchStudents("John",null,null,null,null))
          .willReturn(Arrays.asList(this.students.get(0)));
    
    //When and Then
    this.mockMvc.perform(get("/students/search?firstName=John").accept(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.flag").value(true))
              .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
              .andExpect(jsonPath("$.message").value("Search Success"));
  } 
}
