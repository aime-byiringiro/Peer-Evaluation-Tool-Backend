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

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentService;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;

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

    Student s1 = new Student();
    s1.setId("132");
    s1.setFirstName("John");
    s1.setLastName("Doe");
    this.students.add(s1);

    Student s2 = new Student();
    s2.setId("465");
    s2.setFirstName("Jane");
    s2.setLastName("Doe");
    this.students.add(s2);

    Student s3 = new Student();
    s3.setId("798");
    s3.setFirstName("Bob");
    s3.setLastName("Jones");
    this.students.add(s3);

    Student s4 = new Student();
    s4.setId("1324");
    s4.setFirstName("John");
    s4.setLastName("Smith");
    this.students.add(s4);
  }

  @AfterEach
  void tearDown(){

  }

 @Test
  void testFindStudentByIdSuccess() throws Exception{
    //Given
    given(this.studentService.findById("132")).willReturn(this.students.get(0));
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
    given(this.studentService.findById("132")).willThrow(new ObjectNotFoundException("student","132"));

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
