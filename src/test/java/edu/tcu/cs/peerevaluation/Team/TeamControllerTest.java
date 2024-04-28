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



    @Test
    void  testCreateNewTeam() throws Exception {

        List<CriterionDto> criteriaList = new ArrayList<>();

        criteriaList.add(new CriterionDto(1,
                "How do you rate the quality",
                "Quality of work",
                10));
        criteriaList.add(new CriterionDto(2,
                "How productive is this teammate?",
                "Productivity", 10));

        RubricDto rubricDto = new RubricDto(1,
                "2024 Rubric",
                criteriaList);

        SectionDto sectionDto = new SectionDto(10,
                "Section2025-2026",
                "2025",
                "01/06/2025",
                "02/06/2026",
                rubricDto);

        TeamDto teamDto = new TeamDto(10,
                "TeamName",
                "2025",
                sectionDto,
                null,
                null);

        String json = this.objectMapper.writeValueAsString(teamDto);

        List<Criterion> newCriterionList = new ArrayList<>();
        Criterion c1 = new Criterion();
        c1.setId(1); c1.setDescription("How do you rate the quality"); c1.setMaxScore(10);
        Criterion c2 = new Criterion();
        c2.setId(2); c2.setDescription("How productive is this teammate?"); c2.setMaxScore(10);
        newCriterionList.add(c1);
        newCriterionList.add(c2);

        // Create Rubric object
        Rubric newRubric = new Rubric();
        newRubric.setRubricName("2024 Rubric");
        newRubric.setId(1);
        newRubric.setCriterionList(newCriterionList);

        // Create Section object
        Section savedSection = new Section();
        savedSection.setId(10);

        savedSection.setAcademicYear("2025");
        savedSection.setFirstDay("01/06/2025");
        savedSection.setLastDay("01/06/2026");
        savedSection.setRubric(newRubric);

        /////////////////////////////////////////

        Team savedTeam = new Team();
        savedTeam.setId(10);
        savedTeam.setTeamName("teamName");
        savedTeam.setAcademicYear("2025");
        savedTeam.setSection(savedSection);

        /*
        There is a problem here
         */
//        savedTeam.setStudents(null);
//        savedTeam.setInstructor(null);


        given(this.teamService.save(Mockito.any(Team.class))).willReturn(savedTeam);

        System.out.println(json);

        this.mockMvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.teamName").value(savedTeam.getTeamName()))
                .andExpect(jsonPath("$.data.academicYear").value(savedTeam.getAcademicYear()))
                .andExpect(jsonPath("$.data.section").value(savedTeam.getSection()))
                .andExpect(jsonPath("$.data.students").value(savedTeam.getStudents()))
                .andExpect(jsonPath("$.data.instructor").value(savedTeam.getInstructor()))
        ;
    }



}
