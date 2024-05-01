package edu.tcu.cs.peerevaluation.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluationRepostitory;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.EvaluationRepository;
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
  private final PeerEvaluationRepostitory peerEvaluationRepostitory;
  private final EvaluationRepository evaluationRepository;

  public DataGeneration(StudentRepository studentRepository, RubricRepository rubricRepository, SectionRepository sectionRepository, TeamRepository teamRepository, PeerEvaluationRepostitory peerEvaluationRepostitory, EvaluationRepository evaluationRepository) {
    this.studentRepository = studentRepository;
    this.rubricRepository = rubricRepository;
    this.sectionRepository = sectionRepository;
    this.teamRepository = teamRepository;
    this.peerEvaluationRepostitory = peerEvaluationRepostitory;
    this.evaluationRepository = evaluationRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    Boolean doGenData = true;

    if(doGenData) {

      Section section = genSection();
      this.sectionRepository.save(section);

      List<Student> students = new ArrayList<>();

      for (int i = 0; i < 10; i++) {
        Team team = genTeam();
        this.teamRepository.save(team);
        team.setSection(section);
        team.setAcademicYear(section.getAcademicYear());
        for (int j = 0; j < 5; j++) {
          Student student = genStudent();
          students.add(student);
          this.studentRepository.save(student);
          team.addStudent(student);
          this.studentRepository.save(student);
        }
        this.teamRepository.save(team);
      }

    }
  }

  private Student genStudent() {
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();

    Student student = new Student();
    student.setFirstName(faker.name().firstName());
    student.setMiddleInitial(faker.lorem().characters(1, false, false));
    student.setLastName(faker.name().lastName());
    student.setEmail(faker.internet().emailAddress(student.getFirstName() + "." + student.getLastName()));
    return student;
  }

  private Team genTeam() {
    Locale.setDefault(Locale.US);
    Faker faker = new Faker();
    Team newTeam = new Team();
    newTeam.setTeamName(faker.team().name());
    return newTeam;
  }

  private Section genSection() {
    Section newSection = new Section();
    newSection.setSectionName("Section2024-2025");
    newSection.setAcademicYear("2024");
    newSection.setFirstDay("08/19/2024");
    newSection.setLastDay("05/09/2025");
    newSection.setRubric(genRubric());
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

  // private Evaluation genEvaluation(Student student){
  //   Locale.setDefault(Locale.US);
  //   Faker faker = new Faker();
  //   Evaluation evaluation = new Evaluation();
  //   evaluation.setEvaluated(student);
  //   evaluation.setPrivateComments(faker.yoda().quote());
  //   evaluation.setPublicComments(faker.famousLastWords().lastWords());
  //   List<Integer> scores = new ArrayList<>();
  //   for (int i = 0; i < 6; i++) {
  //     scores.add(faker.number().numberBetween(1,10));
  //   }
  //   evaluation.setScores(scores);
  //   Integer totalScore = scores.stream().reduce(0, (a, b) -> a + b);
  //   evaluation.setTotalScore(totalScore);
  //   return evaluation;
  // }

  // private PeerEvaluation genPeerEvaluation(Student evaluator){
  //   Locale.setDefault(Locale.US);
  //   Faker faker = new Faker();
  //   List<Student> students = evaluator.getTeam().getStudents();
  //   PeerEvaluation peerEval = new PeerEvaluation();
  //   peerEval.setEvaluator(evaluator);
  //   this.peerEvaluationRepostitory.save(peerEval);
  //   List<Evaluation> evals = new ArrayList<>();
  //   students.forEach((student) -> {
  //     Evaluation eval = genEvaluation(student);
  //     eval.setPeerEvaluation(peerEval);
  //     evals.add(eval);
  //   });
  //   peerEval.setEvaluations(evals);

  //   return peerEval;
  // }
}

