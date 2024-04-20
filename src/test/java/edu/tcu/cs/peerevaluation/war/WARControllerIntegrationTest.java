package edu.tcu.cs.peerevaluation.war;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.war.submission.dto.SubmissionDto;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration test for WAR API endpoints")
@Tag("integration")
public class WARControllerIntegrationTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  String token;

  @BeforeEach
  void setUp() throws Exception {
    ResultActions resultActions = this.mockMvc
        .perform(post("/users/login")
            .with(httpBasic("Asuri", "summer2024")));

    MvcResult mvcResult = resultActions.andDo(print()).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();
    JSONObject json = new JSONObject(contentAsString);
    this.token = "Bearer " + json.getJSONObject("data").getString("token");
  }

  // /war , GET
  @Test
  @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
  void testFindAllSubmissionsSuccess() throws Exception {
    this.mockMvc.perform(get("/war")
                  .accept(MediaType.APPLICATION_JSON)
                  .header(HttpHeaders.AUTHORIZATION, this.token))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(6)));
  }

  // /war/id/{submissionId} , GET
  @Test
  void testFindSubmissionByIdSuccess() throws Exception {
    this.mockMvc
        .perform(get("/war/id/1").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find By ID Success"))
        .andExpect(jsonPath("$.data.id").value("1"))
        .andExpect(jsonPath("$.data.teamMember").value("Aliya Suri"))
        .andExpect(jsonPath("$.data.taskCategory").value("Backend dev"))
        .andExpect(jsonPath("$.data.plannedTask").value("Task1"))
        .andExpect(jsonPath("$.data.description").value("description of task"))
        .andExpect(jsonPath("$.data.plannedHours").value(5.0))
        .andExpect(jsonPath("$.data.actualHours").value(6.0))
        .andExpect(jsonPath("$.data.status").value("Done"));


  }

  // /war/{submissionId} , GET
  @Test
  void testFindSubmissionByIdNotFound() throws Exception {
    this.mockMvc
    .perform(get("/war/id/7").accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, this.token))
    .andExpect(jsonPath("$.flag").value(false))
    .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
    .andExpect(jsonPath("$.message").value("Could not find submission with Id 7 :("))
    .andExpect(jsonPath("$.data").isEmpty());
  }

  // /war/{activeWeek} , GET
  @Test
  void testGetByActiveWeek() throws Exception {
    this.mockMvc
    .perform(get("/war/4").accept(MediaType.APPLICATION_JSON)
        .header(HttpHeaders.AUTHORIZATION, this.token))
    .andExpect(jsonPath("$.flag").value(true))
    .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
    .andExpect(jsonPath("$.message").value("Find By Week Success"))
    .andExpect(jsonPath("$.data", Matchers.hasSize(3)));
  }

  // /war/{week} , POST
  @Test
  void testNewSubmission() throws Exception {
    SubmissionDto submissionDto = new SubmissionDto(
      7, 
      null, 
      "Deployment", 
      "Task9", 
      "brief description", 
      4.5, 
      5.0, 
      "In Progress");
      
      String json = this.objectMapper.writeValueAsString(submissionDto);

      this.mockMvc.perform(post("/war/4").contentType(MediaType.APPLICATION_JSON).content(json)
              .accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
          .andExpect(jsonPath("$.flag").value(true))
          .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
          .andExpect(jsonPath("$.message").value("Add Success"))
          .andExpect(jsonPath("$.data.id").isNotEmpty())
          .andExpect(jsonPath("$.data.teamMember").value("Aliya Suri"));
      this.mockMvc.perform(get("/war")
          .accept(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Find All Success"))
        .andExpect(jsonPath("$.data", Matchers.hasSize(7)));


  }

  // /war , PUT
  @Test
  void testUpdateSubmission() throws Exception {
    SubmissionDto submissionDto = new SubmissionDto(
      3, 
      null, 
      "Frontend dev", 
      "Task3", 
      "description of task", 
      4.0, 
      8.0, 
      "Done");

      String json = this.objectMapper.writeValueAsString(submissionDto);

      this.mockMvc.perform(put("/war").contentType(MediaType.APPLICATION_JSON).content(json)
              .accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, this.token))
          .andExpect(jsonPath("$.flag").value(true))
          .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
          .andExpect(jsonPath("$.message").value("Update Success"))
          .andExpect(jsonPath("$.data.id").value(3))
          .andExpect(jsonPath("$.data.teamMember").value("Aliya Suri"));
  }

  // /war/{submissionId} , DELETE
  @Test
  void testDeleteSubmission() throws Exception {
    this.mockMvc.perform(delete("/war/2").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
        .andExpect(jsonPath("$.message").value("Delete Success"));
    this.mockMvc
        .perform(get("/war/id/2").accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, this.token))
        .andExpect(jsonPath("$.flag").value(false))
        .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
        .andExpect(jsonPath("$.message").value("Could not find submission with Id 2 :("))
        .andExpect(jsonPath("$.data").isEmpty());
  }
  
}
