package edu.tcu.cs.peerevaluation.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.RubricRepository;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamRepository;
import net.datafaker.Faker;




@Component
@Profile("!test")
public class DataGeneration implements CommandLineRunner  {

  private final StudentRepository studentRepository;
  private final RubricRepository rubricRepository;
  private final SectionRepository sectionRepository;
  private final TeamRepository teamRepository;


  public DataGeneration(StudentRepository studentRepository, RubricRepository rubricRepository, SectionRepository sectionRepository, TeamRepository teamRepository) {
    this.studentRepository = studentRepository;
    this.rubricRepository = rubricRepository;
    this.sectionRepository = sectionRepository;
    this.teamRepository = teamRepository;
  }


  @Override
  public void run(String... args) throws Exception {
    Section section = genSection();
    this.sectionRepository.save(section);

    List<Student> students = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      Team team = genTeam();
      team.setSection(section);
      for (int j = 0; j < 5; j++) {
        Student student = genStudent();
        students.add(student);
        this.studentRepository.save(student);
        team.addStudentToTeam(student);
      }
      this.teamRepository.save(team);
    }





  }

  private Student genStudent(){
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();

    Student student = new Student();
    student.setFirstName(faker.name().firstName());
    student.setMiddleInitial(faker.lorem().characters(1, false, false));
    student.setLastName(faker.name().lastName());
    student.setEmail(faker.internet().emailAddress(student.getFirstName() + "." + student.getLastName()));
    return student;
  }

  private Evaluation genEvaluation(){
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();
    Evaluation evaluation = new Evaluation();
    return evaluation;
  }

  private PeerEvaluation genPeerEvaluation(){
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();
    PeerEvaluation peerEval = new PeerEvaluation();

    return peerEval;
  }

  private Team genTeam() {
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();
    Team newTeam = new Team();
    newTeam.setTeamName(faker.team().name());
    return newTeam;
  }

  private Section genSection() {
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();
    Section newSection = new Section();
    newSection.setSectionName("Section2024-2025");
    newSection.setAcademicYear("2024");
    newSection.setFirstDay("08/19/2024");
    newSection.setLastDay("05/09/2025");
    return newSection;
  }

  private List<Criterion> genCriterionList() {
    List<Criterion> criterionList = new ArrayList<>();

    Criterion c1 = new Criterion();
      c1.setCriterionName("Quality of work");
      c1.setDescription("How do you rate the quality of this teammate’s work? (1-10)");
      c1.setMaxScore(10);

    Criterion c2 = new Criterion();
      c2.setCriterionName("Productivity");
      c2.setDescription("How productive is this teammate? (1-10)");
      c2.setMaxScore(10);

    Criterion c3 = new Criterion();
      c3.setCriterionName("Proactiveness");
      c3.setDescription("How proactive is this teammate? (1-10)");
      c3.setMaxScore(10);

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

    return criterionList;
  }

  private Rubric genRubric() {
    List<Criterion> criterionList = genCriterionList();
    Rubric newRubric = new Rubric();
      newRubric.setRubricName("2024 Rubric");
      newRubric.setCriterionList(criterionList);
    criterionList.forEach((criterion) -> {
      criterion.setRubricId(newRubric);
    });
    Rubric savedRubric = this.rubricRepository.save(newRubric);
    return savedRubric;
  }

}

