package edu.tcu.cs.peerevaluation.Section;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionController;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.section.SectionService;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;
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
    SectionRepository sectionRepository;

    // @Mock
    // IdWorker idWorker;

    @InjectMocks
    SectionService sectionService;



    @BeforeEach
    void setUp() {


    }
    @AfterEach
    void tearDown() {
    }



    @Test
    void testAdminFindsSeniorDesignSectionsByIdSuccess() {
        // Given, Example of json result

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
        Creating fake section data
        */
        Section section1 = new Section();
        section1.setSectionName("Section2023-2024");
        section1.setAcademicYear("2023");
        section1.setFirstDay("08/21/2023");
        section1.setLastDay("05/01/2024");
        section1.setRubric(r1);

        given(this.sectionRepository.findById(1)).willReturn(Optional.of(section1));
        // when
        Section returnedSection = this.sectionService.findBySectionId(1);
        //Then

        assertThat(returnedSection.getSectionName()).isEqualTo(section1.getSectionName());
        assertThat(returnedSection.getAcademicYear()).isEqualTo(section1.getAcademicYear());
        assertThat(returnedSection.getFirstDay()).isEqualTo(section1.getFirstDay());
        assertThat(returnedSection.getLastDay()).isEqualTo(section1.getLastDay());
        assertThat(returnedSection.getRubric()).isEqualTo(section1.getRubric());
        verify(this.sectionRepository, times(1)).findById(1);


    }

    @Test
    void testSaveSuccess(){
        //Given
        Section newSection = new Section();
        newSection.setId(1);
        newSection.setSectionName("Section2025-2026");
        newSection.setAcademicYear("2025");
        newSection.setFirstDay("06/06/2025");
        newSection.setLastDay("06/06/2026");


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



        Rubric r1 = new Rubric();
        r1.setRubricName("2025 Rubric");
        r1.setCriterionList(criterionList);

        newSection.setRubric(r1);
        //given(this.idWorker.nextId()).willReturn(123456L);
        given(this.sectionRepository.save(newSection)).willReturn(newSection);

        //when
        Section savedSection = this.sectionService.save(newSection);

        //Then
        assertThat(savedSection.getId()).isEqualTo(1);
        assertThat(savedSection.getSectionName()).isEqualTo(newSection.getSectionName());
        assertThat(savedSection.getLastDay()).isEqualTo(newSection.getLastDay());
        assertThat(savedSection.getFirstDay()).isEqualTo(newSection.getFirstDay());

    }

    @Test
    void testEditSuccess(){

        //Given
        Section oldSection = new Section();
        oldSection.setId(10);
        oldSection.setSectionName("Section2023-2024");
        oldSection.setAcademicYear("2023");
        oldSection.setFirstDay("08/21/2023");
        oldSection.setLastDay("05/01/2024");
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



        Rubric r1 = new Rubric();
        r1.setRubricName("2025 Rubric");
        r1.setCriterionList(criterionList);

        oldSection.setRubric(r1);


        Section update = new Section();
        //update.setID()//uneditable
        update.setSectionName("section2026-2027");
        update.setAcademicYear("2026");
        update.setFirstDay("09/08/2026");
        update.setLastDay("06/30/2027");


        List<Criterion> newcriterionList = new ArrayList<>();
        Criterion newc4 = new Criterion();
        c4.setCriterionName("Manners");
        c4.setDescription("Does this teammate treat others with respect? (1-10)");
        c4.setMaxScore(10);

        Criterion newc5 = new Criterion();
        c5.setCriterionName("Humbleness");
        c5.setDescription("How well does this teammate handle criticism of their work? (1-10)");
        c5.setMaxScore(10);

        Criterion newc6 = new Criterion();
        c6.setCriterionName("Engagement in meetings");
        c6.setDescription("How is this teammate's performance during meetings? (1-10)");
        c6.setMaxScore(10);
        criterionList.addAll(Arrays.asList( c4, c5, c6));


        Rubric r2 = new Rubric();
        r1.setRubricName("2025 Rubric");
        r1.setCriterionList(criterionList);
        update.setRubric(r2);


        given(this.sectionRepository.findById(10)).willReturn(Optional.of(oldSection));
        given(this.sectionRepository.save(oldSection)).willReturn(oldSection);

        //when
        Section editedSection = this.sectionService.edit(10, update);

        // Then

        assertThat(editedSection.getId()).isEqualTo(10);
        assertThat(editedSection.getSectionName()).isEqualTo(update.getSectionName());
        assertThat(editedSection.getFirstDay()).isEqualTo(update.getFirstDay());
        assertThat(editedSection.getLastDay()).isEqualTo(update.getLastDay());
        assertThat(editedSection.getRubric()).isEqualTo(update.getRubric());


    }

    @Test
    void testEditNotFound(){
        //Given

        Section edit = new Section();
        edit.setSectionName("Section2024-2025");
        edit.setAcademicYear("2024");
        edit.setFirstDay("06/01/2024");
        edit.setLastDay("01/06/2025");

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



        Rubric r1 = new Rubric();
        r1.setRubricName("2025 Rubric");
        r1.setCriterionList(criterionList);

        edit.setRubric(r1);

        given(this.sectionRepository.findById(10)).willReturn(Optional.empty());

        //when
        assertThrows(ObjectNotFoundException.class, () -> {
            this.sectionService.edit(10, edit);
        });

        //Then
        verify(this.sectionRepository, times(1)).findById(10);
    }

}