package edu.tcu.cs.peerevaluation.instructor;

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
    void testSearchInstructors() {
        // Given
        Instructor i1 = new Instructor();
        i1.setId(1);
        i1.setFirstName("Alice");
        i1.setLastName("Smith");
        
        Instructor i2 = new Instructor();
        i2.setId(2);
        i2.setFirstName("Bob");
        i2.setLastName("Jones");

        List<Instructor> mockInstructors = Arrays.asList(i1, i2);
        given(instructorRepository.findAll(any(Specification.class))).willReturn(mockInstructors);

        // When
        List<Instructor> foundInstructors = instructorService.search("Alice", "Smith", null, null);

        // Then
        assertThat(foundInstructors).isNotNull();
        assertThat(foundInstructors.size()).isEqualTo(2);
        assertThat(foundInstructors.get(0).getFirstName()).isEqualTo("Alice");
        assertThat(foundInstructors.get(0).getLastName()).isEqualTo("Smith");

        // Verify that the repository was called once with any specification
        verify(instructorRepository, times(1)).findAll(any(Specification.class));
    }

}
