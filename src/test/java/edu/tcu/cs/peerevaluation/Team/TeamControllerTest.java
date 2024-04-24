package edu.tcu.cs.peerevaluation.Team;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.section.Section;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.converter.StudentToStudentDtoConverter;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamService;
import edu.tcu.cs.peerevaluation.team.converter.TeamToTeamDtoConverter;
import edu.tcu.cs.peerevaluation.team.dto.TeamDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "dev")
public class TeamControllerTest {

    Team team = new Team();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TeamService teamService;

    @Mock
    TeamToTeamDtoConverter teamToTeamDtoConverter;


    @Mock
    StudentToStudentDtoConverter studentToStudentDtoConverter;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){


    }

    @AfterEach
    void tearDown(){

    }


//    @Test
//    void  testCreateNewTeam() throws Exception {
//
//
//        List<CriterionDto> newCriterionDtolist = new ArrayList<>();
//        CriterionDto criterionDto1 = new CriterionDto(10,
//                "hello hello",
//                "criterionName",
//                10);
//        newCriterionDtolist.add(criterionDto1);
//
//        RubricDto newRubriDto = new RubricDto(10,
//                "rubricName",
//                newCriterionDtolist);
//
//        SectionDto newSectionDto = new SectionDto(10,
//                "sectionName",
//                "2025",
//                "01/06/2023",
//                "01/06/2024",
//                newRubriDto);
//
//        TeamDto teamDto = new TeamDto(10,
//                "teamName",
//                null);
//
//        String json = this.objectMapper.writeValueAsString(teamDto);
//
//        List<Criterion> newCriterionlist = new ArrayList<>();
//        Criterion criterion1 = new Criterion();
//        criterion1.setId(10);
//        criterion1.setCriterionName("criterionName");
//        criterion1.setMaxScore(10);
//        newCriterionlist.add(criterion1);
//
//        Rubric newRubric = new Rubric();
//        newRubric.setId(10);
//        newRubric.setRubricName("rubricName");
//        newRubric.setCriterionList(newCriterionlist);
//
//        Section newSection = new Section();
//        newSection.setId(10);
//        newSection.setSectionName("sectionName");
//        newSection.setAcademicYear("2025");
//        newSection.setFirstDay("01/06/2023");
//        newSection.setLastDay("01/06/2024");
//        newSection.setRubric(newRubric);
//
//        Team savedTeam = new Team();
//        savedTeam.setId(10);
//        savedTeam.setTeamName("teamName");
//        savedTeam.setSection(newSection);
//
//
//        given(this.teamService.save(Mockito.any(Team.class))).willReturn(savedTeam);
//
//        System.out.println(json);
//        this.mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.flag").value(true))
//                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
//                .andExpect(jsonPath("$.message").value("Add Success"))
//                .andExpect(jsonPath("$.data.id").isNotEmpty())
//                .andExpect(jsonPath("$.data.teamName").value(savedTeam.getTeamName()))
//                .andExpect(jsonPath("$.data.section.id").value(savedTeam.getSection().getId()));
//
//
//    }



}
