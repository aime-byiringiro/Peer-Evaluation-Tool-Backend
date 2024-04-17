package edu.tcu.cs.peerevaluation.Section;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.section.Section;
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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class SectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SectionService sectionService;

    @Autowired
    ObjectMapper objectMapper;
    Section section1 = new Section();
    Rubric r1 = new Rubric();

    @BeforeEach
    void setUp(){
          /*
        creating fake creterionList
         */
        List<Criterion> criterionList = new ArrayList<>();

        Criterion c4 = new Criterion();
        c4.setCriterionName("Manners");
        c4.setDescription("Does this teammate treat others with respect? (1-10)");
        c4.setMaxScore(10);

        Criterion c5 = new Criterion();
        c5.setCriterionName("Humbleness");
        c5.setDescription("How well does this teammate handle criticism of their work? (1-10)");
        c5.setMaxScore(10);

        Criterion c6 = new Criterion();
        c6.setCriterionName("Engagement in meetings");
        c6.setDescription("How is this teammate's performance during meetings? (1-10)");
        c6.setMaxScore(10);
        criterionList.addAll(Arrays.asList( c4, c5, c6));

       /*
       Creating fake Rubric
       */
        Rubric r1 = new Rubric();
        r1.setRubricName("2024 Rubric");
        r1.setCriterionList(criterionList);

        /*
        Creating fake section data // Actual
        */
        section1.setId(1);
        section1.setSectionName("Section2023-2024");
        section1.setAcademicYear("2023");
        section1.setFirstDay("08/21/2023");
        section1.setLastDay("05/01/2024");
        section1.setRubric(r1);

    }
    @AfterEach
    void tearDown(){

    }

    @Test
    void testFindSectionBySectionName() throws Exception {
        //Given

        SectionDto sectionDto = new SectionDto(1,
                "Section2023-2024",
                "2023",
                "08/21/2023",
                "05/01/2024",
                null);

        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section foundSection = new Section();
        foundSection.setId(1);
        foundSection.setSectionName("Section2023-2024");
        foundSection.setAcademicYear("2023");
        foundSection.setFirstDay("08/21/2023");
        foundSection.setLastDay("05/01/2024");
        foundSection.setRubric(null);

        given(this.sectionService.adminFindsSeniorDesignSectionsBySectionName(1)).willReturn(this.section1);
        System.out.print(this.section1);

        this.mockMvc.perform(get("/section/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.id").value(foundSection.getId()))
                .andExpect(jsonPath("$.data.sectionName").value(foundSection.getSectionName()))
                .andExpect(jsonPath("$.data.firstDay").value(foundSection.getFirstDay()))
                .andExpect(jsonPath("$.data.lastDay").value(foundSection.getLastDay()))
                .andExpect(jsonPath("$.data.academicYear").value(foundSection.getAcademicYear()));
        // when and then
    }



}
