package edu.tcu.cs.peerevaluation.Section;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionController;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.section.SectionService;
import edu.tcu.cs.peerevaluation.team.Team;
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
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    List<Team> team = new ArrayList<>();
    @Mock
    SectionRepository sectionRepository;

    @InjectMocks
    SectionService sectionService;

    List<Section> sections;

    @BeforeEach
    void setUp() {

//        Section sectionA = new Section();
//        sectionA.setId("123456789");
//        sectionA.setAcademicYear("2024");
//        sectionA.setTeams(team);
//
//       this.sections = new ArrayList<>();
//       this.sections.add(sectionA);


    }

    @AfterEach
    void tearDown() {
    }



    @Test
    void testAdminFindsSeniorDesignSectionsByIdSuccess() {
        // Given, Example of json result

        /*
         sec1.setSectionName("Section2023-2024");
          sec1.setAcademicYear("2023");
          sec1.setFirstDay("08/21/2023");
          sec1.setLastDay("05/01/2024");
          sec1.setRubric(r1);

         */
        Section section1 = new Section();
        section1.setSectionName("Section2023-2024");
        section1.setAcademicYear("08/21/2023");
        section1.setFirstDay("05/01/2024");



        given(this.sectionRepository.findById(123456789)).willReturn(Optional.of(section1));
        // when
        Section returnedSection = this.sectionService.adminFindsSeniorDesignSectionsById("Section2023-2024");
        //Then
        assertThat(returnedSection.getId()).isEqualTo(section1.getId());
        assertThat(returnedSection.getAcademicYear()).isEqualTo(section1.getAcademicYear());
        assertThat(returnedSection.getTeams()).isEqualTo(section1.getTeams());
        verify(this.sectionRepository, times(1)).findById(123456789);




    }



}