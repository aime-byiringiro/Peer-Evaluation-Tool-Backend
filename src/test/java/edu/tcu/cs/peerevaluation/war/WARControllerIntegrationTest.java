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
            .with(httpBasic("john", "123456")));

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

  // /war/{submissionId} , GET
  @Test
  void testFindSubmissionByIdSuccess() throws Exception {

  }

  // /war/{submissionId} , GET
  @Test
  void testFindSubmissionByIdNotFound() throws Exception {

  }

  // /war/{activeWeek} , GET
  @Test
  void testGetByActiveWeek() throws Exception {

  }

  // /war/{week} , POST
  @Test
  void testNewSubmission() throws Exception {

  }

  // /war , PUT
  @Test
  void testUpdateSubmission() throws Exception {

  }

  // /war/{submissionId} , DELETE
  @Test
  void testDeleteSubmission() throws Exception {

  }







}
