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
  void setUp(){

    Instructor i1 = new Instructor();
    i1.setId(1);
    i1.setFirstName("John");
    i1.setLastName("Doe");

    Instructor i2 = new Instructor();
    i2.setId(2);
    i2.setFirstName("Mike");
    i2.setLastName("Smith");

    Instructor i3 = new Instructor();
    i3.setId(3);
    i3.setFirstName("Robert");
    i3.setLastName("Jones");

    instructors.add(i1);
    instructors.add(i2);
    instructors.add(i3);
  }
    
}
