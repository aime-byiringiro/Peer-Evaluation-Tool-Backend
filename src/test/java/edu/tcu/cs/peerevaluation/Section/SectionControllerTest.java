package edu.tcu.cs.peerevaluation.Section;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.converter.RubricToRubricDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.converter.CriterionToCriterionDtoConverter;
import edu.tcu.cs.peerevaluation.rubric.criterion.dto.CriterionDto;
import edu.tcu.cs.peerevaluation.rubric.dto.RubricDto;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.print.attribute.standard.Media;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "dev")
public class SectionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SectionService sectionService;

    @Mock
    RubricToRubricDtoConverter  rubricToRubricDtoConverter;

    @Mock
    CriterionToCriterionDtoConverter criterionToCriterionDtoConverter;



    @Autowired
    ObjectMapper objectMapper;
    Section section1 = new Section();
    Rubric r1 = new Rubric();
    RubricDto rubricDto;

    List<Criterion> criterionList = new ArrayList<>();
    List<CriterionDto> criterionDtos = new ArrayList<>();



    @BeforeEach
    void setUp(){
          /*
        creating fake creterionList
         */

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
        
        criterionDtos.add(this.criterionToCriterionDtoConverter.convert(c4));
        criterionDtos.add(this.criterionToCriterionDtoConverter.convert(c5));
        criterionDtos.add(this.criterionToCriterionDtoConverter.convert(c6));



       /*
       Creating fake Rubric
       */
        r1.setId(1);
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


        //RubricDto rubricDtoData = new RubricDto(6, "2025 Rubric", criterionList);


    }
    @AfterEach
    void tearDown(){

    }

    @Test
    void testFindSectionBySectionID() throws Exception {
        rubricDto = this.rubricToRubricDtoConverter.convert(r1);
        //Given
        SectionDto sectionDto = new SectionDto(1,
                "Section2023-2024",
                "2023",
                "08/21/2023",
                "05/01/2024",
                rubricDto);

        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section foundSection = new Section();
        foundSection.setId(1);
        foundSection.setSectionName("Section2023-2024");
        foundSection.setAcademicYear("2023");
        foundSection.setFirstDay("08/21/2023");
        foundSection.setLastDay("05/01/2024");
        foundSection.setRubric(r1);

        given(this.sectionService.adminFindsSeniorDesignSectionsBySectionID(1)).willReturn(this.section1);
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

    @Test
    void testCreateNewSection() throws Exception {

        //Given

        Rubric rubric = new Rubric();
        rubric.setId(6);
        rubric.setRubricName("2025 Rubric");
        rubric.setCriterionList(criterionList);



        RubricDto rubricDtoData = new RubricDto(6, "2025 Rubric", criterionDtos);

        SectionDto sectionDto = new SectionDto(
                5,
                "Section2025-2026",
                "2025",
                "06/06/2025",
                "06/06/2026",
                rubricDtoData);
       
        String json = this.objectMapper.writeValueAsString(sectionDto);

        Section savedSection = new Section();

        savedSection.setId(5);
        savedSection.setSectionName("Section2025-2026");
        savedSection.setAcademicYear("2025");
        savedSection.setFirstDay("06/06/2025");
        savedSection.setLastDay("06/06/2026");
        savedSection.setRubric(rubric);
        given(this.sectionService.save(Mockito.any(Section.class))).willReturn(savedSection);

        // when and then
        this.mockMvc.perform(post("/section").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.sectionName").value(savedSection.getSectionName()))
                .andExpect(jsonPath("$.data.academicYear").value(savedSection.getAcademicYear()))
                .andExpect(jsonPath("$.data.firstDay").value(savedSection.getFirstDay()))
                .andExpect(jsonPath("$.data.lastDay").value(savedSection.getLastDay()));

    }






}