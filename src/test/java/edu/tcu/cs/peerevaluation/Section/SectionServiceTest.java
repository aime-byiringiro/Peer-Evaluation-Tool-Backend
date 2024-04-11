package edu.tcu.cs.peerevaluation.Section;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.section.SectionService;
import edu.tcu.cs.peerevaluation.team.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SectionServiceTest {

    List<Team> team = new ArrayList<>();
    @Mock
    SectionRepository sectionRepository;

    @InjectMocks
    SectionService sectionService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void adminFindsSeniorDesignSectionsByIdSuccess() {

        //Given //
        Section a = new Section();
        a.setId("A");
        a.setAcademicYear("2024");
        a.setTeams(team);

    }

}