package edu.tcu.cs.peerevaluation.student;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.peerevaluation.peerEvalUser.dto.UserDto;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for Student API endpoints")
@Tag("integration")
public class StudentControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  String token;

  @BeforeEach
  void setUp() throws Exception {
    ResultActions resultActions = this.mockMvc
        .perform(post("/users/login")
            .with(httpBasic("john", "123456")));

    MvcResult mvcResult = resultActions.andDo(print()).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();
    JSONObject json = new JSONObject(contentAsString);
    this.token = "Bearer " + json.getJSONObject("data").getString("token");
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  // Reset H2 database before calling this test case.
  void testFindAllStudentsSuccess() throws Exception {
    this.mockMvc
        .perform(get("/students").accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,
            this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find All Success"))
        .andExpect(jsonPath("$.data", Matchers.hasSize(6)));
  }

  @Test
  void testFindStudentByIdSuccess() throws Exception {
    this.mockMvc
        .perform(get("/students/1").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find One Success"))
        .andExpect(jsonPath("$.data.id").value("1"))
        .andExpect(jsonPath("$.data.firstName").value("Aliya"));
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void testFindStudentByIdNotFound() throws Exception {
    this.mockMvc
        .perform(get("/students/7").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 7 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void testFindStudentByFirstNameSuccess() throws Exception {
    this.mockMvc.perform(get("/students/search?firstName=John").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[1].id").value(3))
        .andExpect(jsonPath("$.data[0].id").value(4));
  }

  @Test
  void testFindStudentByLastNameSuccess() throws Exception {
    this.mockMvc.perform(get("/students/search?lastName=Smith").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[1].id").value(3))
        .andExpect(jsonPath("$.data[0].id").value(5));
  }

  @Test
  void testFindStudentBySectionSuccess() throws Exception {
    this.mockMvc.perform(get("/students/search?section=Section2023-2024").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[3].id").value(1))
        .andExpect(jsonPath("$.data[2].id").value(3))
        .andExpect(jsonPath("$.data[1].id").value(2))
        .andExpect(jsonPath("$.data[0].id").value(4));
  }

  @Test
  void testFindStudentByAcademicYearSuccess() throws Exception {
    this.mockMvc.perform(get("/students/search?academicYear=2023").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[3].id").value(1))
        .andExpect(jsonPath("$.data[2].id").value(3))
        .andExpect(jsonPath("$.data[1].id").value(2))
        .andExpect(jsonPath("$.data[0].id").value(4));
  }

  @Test
  void testFindStudentByTeamNameSuccess() throws Exception {
    this.mockMvc.perform(get("/students/search?teamName=SuperfrogScheduler").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Search Success"))
        .andExpect(jsonPath("$.data[1].id").value(3))
        .andExpect(jsonPath("$.data[0].id").value(4));
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void testAddStudentSuccess() throws Exception {
    StudentDto studentDto = new StudentDto(7,
        "Kate",
        "S",
        "Badbarz",
        null,
        null);
    UserDto userDto = new UserDto(7, 
        "Kbadbarz", 
        "password", 
        true, 
        "student");

    StudentUserCombined studentUserCombined = new StudentUserCombined(studentDto,userDto);
    String json = this.objectMapper.writeValueAsString(studentUserCombined);
    System.out.println(json);

    this.mockMvc
        .perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(json)
            .accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Add Success"))
        .andExpect(jsonPath("$.data.id").isNotEmpty())
        .andExpect(jsonPath("$.data.firstName").value("Kate"))
        .andExpect(jsonPath("$.data.middleInitial").value("S"))
        .andExpect(jsonPath("$.data.lastName").value("Badbarz"));
    this.mockMvc
        .perform(get("/students").accept(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find All Success"))
        .andExpect(jsonPath("$.data", Matchers.hasSize(7)));
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void testAddStudentErrorWithInvalidInput() throws Exception {
    StudentDto studentDto = new StudentDto(7,
        "",
        "S",
        "",
        null,
        null);
    UserDto userDto = new UserDto(7, 
        "Kbadbarz", 
        "password", 
        true, 
        "student");

    StudentUserCombined studentUserCombined = new StudentUserCombined(studentDto,userDto);
    String json = this.objectMapper.writeValueAsString(studentUserCombined);

    this.mockMvc
        .perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(json)
            .accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
        .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
        .andExpect(jsonPath("$.data.['studentDto.firstName']").value("first name is required."))
        .andExpect(jsonPath("$.data.['studentDto.lastName']").value("last name is required."));
    this.mockMvc
        .perform(get("/students").accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION,
            this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find All Success"))
        .andExpect(jsonPath("$.data", Matchers.hasSize(6)));
  }

  @Test
  void testUpdateStudentSuccess() throws Exception {
    StudentDto studentDto = new StudentDto(1,
        "Kate",
        "S",
        "Badbarz",
        null,
        null);

    String json = this.objectMapper.writeValueAsString(studentDto);

    this.mockMvc
        .perform(put("/students/1").contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Update Success"))
        .andExpect(jsonPath("$.data.id").value("1"))
        .andExpect(jsonPath("$.data.firstName").value("Kate"))
        .andExpect(jsonPath("$.data.middleInitial").value("S"))
        .andExpect(jsonPath("$.data.lastName").value("Badbarz"));
  }

  @Test
  void testUpdateStudentErrorWithNonExistentId() throws Exception {
    StudentDto studentDto = new StudentDto(7,
        "Kate",
        "S",
        "Badbarz",
        null,
        null);

    String json = this.objectMapper.writeValueAsString(studentDto);

    this.mockMvc
        .perform(put("/students/8").contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 8 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  void testUpdateStudentErrorWithInvalidInput() throws Exception {
    StudentDto studentDto = new StudentDto(1,
        "",
        "S",
        "",
        null,
        null);

    String json = this.objectMapper.writeValueAsString(studentDto);

    this.mockMvc
        .perform(put("/students/1").contentType(MediaType.APPLICATION_JSON)
            .content(json).accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
        .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
        .andExpect(jsonPath("$.data.firstName").value("first name is required."))
        .andExpect(jsonPath("$.data.lastName").value("last name is required."));
    this.mockMvc
        .perform(get("/students/1").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find One Success"))
        .andExpect(jsonPath("$.data.id").value("1"))
        .andExpect(jsonPath("$.data.firstName").value("Aliya"));
  }

  @Test
  void testDeleteStudentSuccess() throws Exception {
    this.mockMvc
        .perform(delete("/students/6").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Delete Success"));
    this.mockMvc
        .perform(get("/students/6").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 6 :("))
        .andExpect(jsonPath("$.data").isEmpty());
    this.mockMvc
        .perform(get("/users/6").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find user with Id 6 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void testDeleteStudentErrorWithNonExistentId() throws Exception {
    this.mockMvc
        .perform(delete("/students/7").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find student with Id 7 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }

}
