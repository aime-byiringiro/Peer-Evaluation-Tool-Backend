package edu.tcu.cs.peerevaluation.Team;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.converter.CriterionToCriterionDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.section.SectionService;

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

import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.TeamRepository;
import edu.tcu.cs.peerevaluation.team.TeamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.print.attribute.standard.Media;
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




}
