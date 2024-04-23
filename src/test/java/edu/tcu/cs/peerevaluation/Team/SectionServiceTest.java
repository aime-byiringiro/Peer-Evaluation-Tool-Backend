package edu.tcu.cs.peerevaluation.Team;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.utils.IdWorker;
import edu.tcu.cs.peerevaluation.section.SectionController;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.section.SectionService;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamRepository;
import edu.tcu.cs.peerevaluation.team.TeamService;
import org.h2.command.dml.Update;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamService teamService;

    @BeforeEach
    void setUp(){
    }

    @AfterEach
    void tearDown(){
    }


    @Test
    void testSaveSuccess(){

        Team newTeam = new Team();
        newTeam.setId(10);
        newTeam.setTeamName("PeerEvaluationTool");
        Section newSection = new Section();
        newSection.setId(10);
        newSection.setSectionName("SectionName");
        newSection.setAcademicYear("2022");
        newSection.setFirstDay("09/05");
        newSection.setLastDay("10/28");
        Rubric newRubric = new Rubric();
        newRubric.setRubricName("newRubric");
        newRubric.setId(10);
        List<Criterion> newCriterionList = new ArrayList<>();
        Criterion criterion1 = new Criterion();
        criterion1.setId(10);
        criterion1.setCriterionName("criterionName");
        criterion1.setDescription("hello hello");
        criterion1.setMaxScore(10);
        newCriterionList.add(criterion1);
        newRubric.setCriterionList(newCriterionList);
        newSection.setRubric(newRubric);
        newTeam.setSection(newSection);

        given(teamRepository.save(newTeam)).willReturn(newTeam);

        Team savedTeam = this.teamService.save(newTeam);

        assertThat(savedTeam.getId()).isEqualTo(10);
        assertThat(savedTeam.getTeamName()).isEqualTo(newTeam.getTeamName());
        assertThat(savedTeam.getSection()).isEqualTo(newTeam.getSection());

    }


}
