package edu.tcu.cs.peerevaluation.Team;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamRepository;
import edu.tcu.cs.peerevaluation.team.TeamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamService teamService;

    List<Team> teams;

    @BeforeEach
    void setUp() {
        Student s1 = new Student();
        s1.setFirstName("Aliya");
        s1.setMiddleInitial("S");
        s1.setLastName("Suri");
        s1.setEmail("aliya.suri@tcu.edu");

        Student s2 = new Student();
        s2.setFirstName("James");
        s2.setMiddleInitial("R");
        s2.setLastName("Edmonson");
        s2.setEmail("james.edmonson@tcu.edu");

        Student s3 = new Student();
        s3.setFirstName("John");
        s3.setMiddleInitial("P");
        s3.setLastName("Smith");
        s3.setEmail("john.smith@tcu.edu");

        Student s4 = new Student();
        s4.setFirstName("John");
        s4.setMiddleInitial("B");
        s4.setLastName("Doe");
        s4.setEmail("john.doe@tcu.edu");

        Student s5 = new Student();
        s5.setFirstName("Aaron");
        s5.setMiddleInitial("D");
        s5.setLastName("Smith");

        s5.setEmail("aaron.smith@tcu.edu");
        Student s6 = new Student();
        s6.setFirstName("Jake");
        s6.setMiddleInitial("F");
        s6.setLastName("Farm");
        s6.setEmail("jake.statefarm@gmail.com");

        Team team1 = new Team();
        team1.setTeamName("PeerEvaluation");
        team1.setAcademicYear("Fall 2022");

        Team team2 = new Team();
        team2.setTeamName("SuperfrogScheduler");
        team2.setAcademicYear("Spring 2023");

        Team team3 = new Team();
        team3.setTeamName("MoningMeteorite");
        team3.setAcademicYear("Fall 2024");

        this.teams = new ArrayList<>();
        this.teams.add(team1);
        this.teams.add(team2);
        this.teams.add(team3);

        Section sec1 = new Section();
        sec1.setSectionName("Section2023-2024");
        sec1.setAcademicYear("2023");
        sec1.setFirstDay("08/21/2023");
        sec1.setLastDay("05/01/2024");

        Section sec2 = new Section();
        sec2.setSectionName("Section2024-2025");
        sec2.setAcademicYear("2024");
        sec2.setFirstDay("08/21/2024");
        sec2.setLastDay("05/01/2025");

        Instructor i1 = new Instructor();
        i1.setFirstName("Liran");
        i1.setLastName("Ma");
        i1.setEmail("l.ma@tcu.edu");

        s1.setTeam(team1);
        s2.setTeam(team1);
        s3.setTeam(team2);
        s4.setTeam(team2);
        s5.setTeam(team3);
        s6.setTeam(team3);
        team1.setSection(sec1);
        team2.setSection(sec1);
        team3.setSection(sec2);
        team1.setStudents(Arrays.asList(s1, s2));
        team2.setStudents(Arrays.asList(s3, s4));
        team3.setStudents(Arrays.asList(s5, s6));
        sec1.setTeams(Arrays.asList(team1, team2));
        sec2.setTeams(Arrays.asList(team3));
        team1.setInstructor(i1);
        team2.setInstructor(i1);
        team3.setInstructor(i1);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testFindAllSuccess() {
        given(this.teamRepository.findAll()).willReturn(this.teams);
        List<Team> actualTeams = this.teamService.findAll();
        assertThat(actualTeams.size()).isEqualTo(this.teams.size());
        verify(this.teamRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        Team team = new Team();
        team.setId(1);
        team.setTeamName("SuperfrogScheduler");
        team.setAcademicYear("2023");

        given(this.teamRepository.findById(1)).willReturn(Optional.of(team));

        Team returnedTeam = this.teamService.findById(1);

        assertThat(returnedTeam.getId()).isEqualTo(team.getId());
        assertThat(returnedTeam.getTeamName()).isEqualTo(team.getTeamName());
        assertThat(returnedTeam.getAcademicYear()).isEqualTo(team.getAcademicYear());

        verify(this.teamRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        given(this.teamRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            Team returnedTeam = this.teamService.findById(1);
        });

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find team with Id 1 :(");

        verify(this.teamRepository, times(1)).findById(Mockito.any(Integer.class));
    }

}
