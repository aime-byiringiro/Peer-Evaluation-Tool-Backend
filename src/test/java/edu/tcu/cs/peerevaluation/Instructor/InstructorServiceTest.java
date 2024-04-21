package edu.tcu.cs.peerevaluation.Instructor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.instructor.InstructorService;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceTest {

  @Mock
  InstructorRepository instructorRepository;

  @InjectMocks
  InstructorService instructorService;

  List<Instructor> instructors = new ArrayList<>();

  @BeforeEach
  void setUp() {

    // Initialize Sections
    Section sec1 = new Section();
    sec1.setSectionName("section 1");
    sec1.setAcademicYear("2024");

    Section sec2 = new Section();
    sec2.setSectionName("section 2");
    sec2.setAcademicYear("2025");

    // Initialize Teams and associate them with Sections
    Team team1 = new Team();
    team1.setTeamName("team1");
    team1.setSection(sec1);

    Team team2 = new Team();
    team2.setTeamName("team2");
    team2.setSection(sec2);

    // Initialize team lists
    List<Team> team1List = new ArrayList<>();
    team1List.add(team1);

    List<Team> team2List = new ArrayList<>();
    team2List.add(team2);

    // Assign Teams to Sections
    sec1.setTeams(team1List);
    sec2.setTeams(team2List);

    // Initialize Instructors
    Instructor i1 = new Instructor();
    i1.setId(1);
    i1.setFirstName("John");
    i1.setLastName("Doe");
    i1.setTeams(new ArrayList<>(team1List)); // Set team list with initialized list

    Instructor i2 = new Instructor();
    i2.setId(2);
    i2.setFirstName("Mike");
    i2.setLastName("Smith");
    i2.setTeams(new ArrayList<>()); // Set an empty team list

    Instructor i3 = new Instructor();
    i3.setId(3);
    i3.setFirstName("Robert");
    i3.setLastName("Jones");
    i3.setTeams(new ArrayList<>(team2List)); // Set team list with initialized list

    // Initialize PeerEvalUsers and associate with Instructors
    PeerEvalUser user1 = new PeerEvalUser();
    user1.setEnabled(true);
    user1.setInstructor(i1);
    i1.setUser(user1);

    PeerEvalUser user2 = new PeerEvalUser();
    user2.setEnabled(true);
    user2.setInstructor(i2);
    i2.setUser(user2);

    PeerEvalUser user3 = new PeerEvalUser();
    user3.setEnabled(true);
    user3.setInstructor(i3);
    i3.setUser(user3);

    // Prepare the list of instructors
    instructors = Arrays.asList(i1, i2, i3);
  }

  @AfterEach
  void tearDown() {

  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindInstructorByFirstNameSuccess() {
    // Given
    String searchFirstName = "Mike";
    List<Instructor> expectedInstructors = instructors.stream()
        .filter(i -> searchFirstName.equals(i.getFirstName()))
        .collect(Collectors.toList());

    given(instructorRepository.findAll((Specification<Instructor>) any())).willReturn(expectedInstructors);

    // When
    List<Instructor> result = instructorService.searchInstructors(searchFirstName, null, null, null, true);

    // Then
    assertThat(result).hasSize(expectedInstructors.size());
    assertThat(result.get(0).getFirstName()).isEqualTo(searchFirstName);
    assertThat(result.get(0).getLastName()).isEqualTo(expectedInstructors.get(0).getLastName());
    verify(instructorRepository, times(1)).findAll((Specification<Instructor>) any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindInstructorByAcademicYearSuccess() {
    // Given
    String searchAcademicYear = "2024";
    List<Instructor> expectedInstructors = instructors.stream()
        .filter(i -> i.getTeams().stream()
            .anyMatch(t -> searchAcademicYear.equals(t.getSection().getAcademicYear())))
        .collect(Collectors.toList());

    given(instructorRepository.findAll((Specification<Instructor>) any())).willReturn(expectedInstructors);

    // When
    List<Instructor> result = instructorService.searchInstructors(null, null, searchAcademicYear, null, true);

    // Then
    assertThat(result).hasSize(expectedInstructors.size());
    for (int i = 0; i < result.size(); i++) {
      assertThat(result.get(i).getFirstName()).isEqualTo(expectedInstructors.get(i).getFirstName());
      assertThat(result.get(i).getLastName()).isEqualTo(expectedInstructors.get(i).getLastName());
    }
    verify(instructorRepository, times(1)).findAll((Specification<Instructor>) any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindInstructorByTeamNameSuccess() {
    // Given
    String searchTeamName = "team2";
    List<Instructor> expectedInstructors = instructors.stream()
        .filter(i -> i.getTeams().stream()
            .anyMatch(t -> searchTeamName.equals(t.getTeamName())))
        .collect(Collectors.toList());

    given(instructorRepository.findAll((Specification<Instructor>) any())).willReturn(expectedInstructors);

    // When
    List<Instructor> result = instructorService.searchInstructors(null, null, null, searchTeamName, true);

    // Then
    assertThat(result).hasSize(expectedInstructors.size());
    assertThat(result.get(0).getFirstName()).isEqualTo(expectedInstructors.get(0).getFirstName());
    assertThat(result.get(0).getLastName()).isEqualTo(expectedInstructors.get(0).getLastName());
    verify(instructorRepository, times(1)).findAll((Specification<Instructor>) any());
  }

  @SuppressWarnings("unchecked")
  @Test
  void testFindInstructorByStatusSuccess() {
    // Given
    boolean searchStatus = true; // Change to false if you want to test for disabled instructors
    List<Instructor> expectedInstructors = instructors.stream()
        .filter(i -> i.getUser() != null && i.getUser().isEnabled() == searchStatus)
        .collect(Collectors.toList());

    given(instructorRepository.findAll((Specification<Instructor>) any())).willReturn(expectedInstructors);

    // When
    List<Instructor> result = instructorService.searchInstructors(null, null, null, null, searchStatus);

    // Then
    assertThat(result).hasSize(expectedInstructors.size());
    assertThat(result).allSatisfy(instructor -> {
      assertThat(instructor.getUser()).isNotNull();
      assertThat(instructor.getUser().isEnabled()).isEqualTo(searchStatus);
    });
    verify(instructorRepository, times(1)).findAll((Specification<Instructor>) any());
  }

  @Test
  void testFindInstructorByIdSuccess() {
    // Given
    Instructor i = new Instructor();
    i.setId(132);
    i.setFirstName("John");
    i.setLastName("Doe");

    given(instructorRepository.findById(132)).willReturn(Optional.of(i)); // Defines the behavior of mock object

    // When
    Instructor returnedInstructor = instructorService.findById(132);

    // Then
    assertThat(returnedInstructor.getId()).isEqualTo(i.getId());
    assertThat(returnedInstructor.getFirstName()).isEqualTo(i.getFirstName());
    assertThat(returnedInstructor.getLastName()).isEqualTo(i.getLastName());
    verify(instructorRepository, times(1)).findById(132);
  }

  @Test
  void testFindInstructorByIdNotFound() {
    // Given
    given(instructorRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

    // When
    Throwable thrown = catchThrowable(() -> {
      @SuppressWarnings("unused")
      Instructor returnedInstructor = instructorService.findById(1);
    });

    // Then
    assertThat(thrown)
            .isInstanceOf(ObjectNotFoundException.class)
            .hasMessage("Could not find instructor with Id 1 :(");
    verify(instructorRepository, times(1)).findById(1);
  }

}
