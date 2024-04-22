package edu.tcu.cs.peerevaluation.instructor;

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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.InstructorService;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserService;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.student.dto.StudentDto;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.system.exception.ObjectNotFoundException;
import edu.tcu.cs.peerevaluation.team.Team;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class InstructorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InstructorService instructorService;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    List<Instructor> instructors;

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
        // i1.setTeams(new ArrayList<>(team1List)); // Set team list with initialized list

        Instructor i2 = new Instructor();
        i2.setId(2);
        i2.setFirstName("Mike");
        i2.setLastName("Smith");
        // i2.setTeams(new ArrayList<>()); // Set an empty team list

        Instructor i3 = new Instructor();
        i3.setId(3);
        i3.setFirstName("Robert");
        i3.setLastName("Jones");
        // i3.setTeams(new ArrayList<>(team2List)); // Set team list with initialized list

        // Initialize PeerEvalUsers and associate with Instructors
        PeerEvalUser user1 = new PeerEvalUser();
        user1.setEnabled(true);
        // user1.setInstructor(i1);
        // i1.setUser(user1);

        PeerEvalUser user2 = new PeerEvalUser();
        user2.setEnabled(true);
        // user2.setInstructor(i2);
        // i2.setUser(user2);

        PeerEvalUser user3 = new PeerEvalUser();
        user3.setEnabled(true);
        // user3.setInstructor(i3);
        // i3.setUser(user3);

        // Prepare the list of instructors
        instructors = Arrays.asList(i1, i2, i3);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindInstructorByIdSuccess() throws Exception {
        // Given
        given(this.instructorService.findById(1)).willReturn(this.instructors.get(0));

        // When and then
        this.mockMvc.perform(get("/instructors/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("John"));
    }

    @Test
    void testFindInstructorByIdNotFound() throws Exception {
        // Given
        given(this.instructorService.findById(132)).willThrow(new ObjectNotFoundException("instructor", "132"));

        // When and Then
        this.mockMvc.perform(get("/instructors/132").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find instructor with Id 132 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testSearchInstructorsSuccess() throws Exception {
        // Given
        given(instructorService.search(null, null, null, null)).willReturn(Arrays.asList(instructors.get(0)));

        // When and then
        mockMvc.perform(get("/instructors/search")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Search Success"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].firstName").value("John"))
                .andExpect(jsonPath("$.data[0].lastName").value("Doe"));
    }

    @Test
    void testSearchInstructorsNotFound() throws Exception {
        // Given
        given(instructorService.search("Alice", "Wonderland", null, null)).willReturn(new ArrayList<>());

        // When and then
        mockMvc.perform(get("/instructors/search")
                .param("firstName", "Alice")
                .param("lastName", "Wonderland")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Search Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }



    
}
