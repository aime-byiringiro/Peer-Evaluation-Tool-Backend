package edu.tcu.cs.peerevaluation.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserService;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluationRepostitory;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.RubricRepository;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.rubric.criterion.CriterionRepository;
import edu.tcu.cs.peerevaluation.section.Section;
import edu.tcu.cs.peerevaluation.section.SectionRepository;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;
import edu.tcu.cs.peerevaluation.team.Team;
import edu.tcu.cs.peerevaluation.team.TeamRepository;
import edu.tcu.cs.peerevaluation.war.WAR;
import edu.tcu.cs.peerevaluation.war.WARService;
import edu.tcu.cs.peerevaluation.war.submission.Submission;

@Component
@Profile("!test")
public class DBDataInitializer implements CommandLineRunner {

  private final StudentRepository studentRepository;
  private final PeerEvaluationRepostitory peerEvalRepository;
  private final InstructorRepository instructorRepository;
  private final RubricRepository rubricRepository;
  private final CriterionRepository criterionRepository;
  private final SectionRepository sectionRepository;
  private final TeamRepository  teamRepository;
  private final UserService userService;
  private final WARService warService;

  public DBDataInitializer(StudentRepository studentRepository, PeerEvaluationRepostitory peerEvalRepository, InstructorRepository instructorRepository, RubricRepository rubricRepository, CriterionRepository criterionRepository, SectionRepository sectionRepository, TeamRepository teamRepository, UserService userService, WARService warService) {
    this.studentRepository = studentRepository;
    this.peerEvalRepository = peerEvalRepository;
    this.instructorRepository = instructorRepository;
    this.rubricRepository = rubricRepository;
    this.criterionRepository = criterionRepository;
    this.sectionRepository = sectionRepository;
    this.teamRepository = teamRepository;
    this.userService = userService;
    this.warService = warService;
  }

  @Override
  public void run(String... args) throws Exception {

    // -------------------//
    // Test Student Data //
    // -------------------//

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

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));

    PeerEvalUser s1u = new PeerEvalUser();
    s1u.setUsername("Asuri");
    s1u.setPassword("summer2024");
    s1u.setEnabled(true);
    s1u.setRoles("student user");
    PeerEvalUser s2u = new PeerEvalUser();
    s2u.setUsername("Jedmonson");
    s2u.setPassword("pizzaLover!2");
    s2u.setEnabled(true);
    s2u.setRoles("student");
    PeerEvalUser s3u = new PeerEvalUser();
    s3u.setUsername("Jsmith");
    s3u.setPassword("helloSunshine!");
    s3u.setEnabled(true);
    s3u.setRoles("student");
    PeerEvalUser s4u = new PeerEvalUser();
    s4u.setUsername("Jdoe");
    s4u.setPassword("coffee4Me:)");
    s4u.setEnabled(true);
    s4u.setRoles("student");
    PeerEvalUser s5u = new PeerEvalUser();
    s5u.setUsername("Asmith");
    s5u.setPassword("bookWorm123!");
    s5u.setEnabled(true);
    s5u.setRoles("student");
    PeerEvalUser s6u = new PeerEvalUser();
    s6u.setUsername("Jfarm");
    s6u.setPassword("tractor!");
    s6u.setEnabled(true);
    s6u.setRoles("student");

    this.userService.save(s1u);
    this.userService.save(s2u);
    this.userService.save(s3u);
    this.userService.save(s4u);
    this.userService.save(s5u);
    this.userService.save(s6u);

    s1u.setStudent(s1);
    // s2u.setStudent(s2);
    // s3u.setStudent(s3);
    // s4u.setStudent(s4);
    // s5u.setStudent(s5);
    // s6u.setStudent(s6);

    this.userService.update(s1u.getId(), s1u);
    this.userService.update(s2u.getId(), s2u);
    this.userService.update(s3u.getId(), s3u);
    this.userService.update(s4u.getId(), s4u);
    this.userService.update(s5u.getId(), s5u);
    this.userService.update(s6u.getId(), s6u);

    s1.setUser(s1u);
    s2.setUser(s2u);
    s3.setUser(s3u);
    s4.setUser(s4u);
    s5.setUser(s5u);
    s6.setUser(s6u);

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));

    // Create some users.
    PeerEvalUser u1 = new PeerEvalUser();
    u1.setUsername("john");
    u1.setPassword("123456");
    u1.setEnabled(true);
    u1.setRoles("admin user");
    this.userService.save(u1);

    // ---------------------//
    // Test Criterion Data //
    // ---------------------//
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

    criterionList.addAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
    criterionRepository.saveAll(criterionList);

    // ------------------//
    // Test Rubric Data //
    // ------------------//

    Rubric r1 = new Rubric();
    r1.setRubricName("2024 Rubric");
    r1.addCriterion(c6);
    rubricRepository.save(r1);

    c1.setRubricId(r1);
    c2.setRubricId(r1);
    c3.setRubricId(r1);
    c4.setRubricId(r1);
    c5.setRubricId(r1);
    c6.setRubricId(r1);
    criterionRepository.saveAll(criterionList);

    // ----------------//
    // Test Team Data //
    // ----------------//

    Team team1 = new Team();
    team1.setTeamName("PeerEvaluation");
    team1.setAcademicYear("Fall 2022");

    teamRepository.save(team1);

    Team team2 = new Team();
    team2.setTeamName("SuperfrogScheduler");
    team2.setAcademicYear("Spring 2023");
    teamRepository.save(team2);

    Team team3 = new Team();
    team3.setTeamName("MoningMeteorite");
    team3.setAcademicYear("Fall 2024");
    teamRepository.save(team3);

    // -------------------//
    // Test Section Data //
    // -------------------//

    Section sec1 = new Section();
    sec1.setSectionName("Section2023-2024");
    sec1.setAcademicYear("2023");
    sec1.setFirstDay("08/21/2023");
    sec1.setLastDay("05/01/2024");
    sec1.setRubric(r1);
    sectionRepository.save(sec1);
    Section sec2 = new Section();
    sec2.setSectionName("Section2024-2025");
    sec2.setAcademicYear("2024");
    sec2.setFirstDay("08/21/2024");
    sec2.setLastDay("05/01/2025");
    sec2.setRubric(r1);
    sectionRepository.save(sec2);

    // ---------------------//
    // Test Instructor Data //
    // ---------------------//
    Instructor i1 = new Instructor();
    i1.setFirstName("Liran");
    i1.setLastName("Ma");
    i1.setEmail("l.ma@tcu.edu");
    instructorRepository.save(i1);

    // --------------------------//
    // Test Peer Evaluation Data //
    // --------------------------//

    PeerEvaluation peerEval1 = new PeerEvaluation();
    peerEvalRepository.save(peerEval1);
    peerEval1.setEvaluator(s1);
    peerEval1.setEvaluations(generateEvals(s2, s3, s4, s5, peerEval1));
    peerEval1.setWeek(4);
    peerEvalRepository.save(peerEval1);

    PeerEvaluation peerEval2 = new PeerEvaluation();
    peerEvalRepository.save(peerEval2);
    peerEval2.setEvaluator(s2);
    peerEval2.setEvaluations(generateEvals(s1, s3, s4, s5, peerEval2));
    peerEval2.setWeek(4);
    peerEvalRepository.save(peerEval2);

    PeerEvaluation peerEval3 = new PeerEvaluation();
    peerEvalRepository.save(peerEval3);
    peerEval3.setEvaluator(s3);
    peerEval3.setEvaluations(generateEvals(s2, s1, s4, s5, peerEval3));
    peerEval3.setWeek(4);
    peerEvalRepository.save(peerEval3);

    PeerEvaluation peerEval4 = new PeerEvaluation();
    peerEvalRepository.save(peerEval4);
    peerEval4.setEvaluator(s4);
    peerEval4.setEvaluations(generateEvals(s2, s3, s1, s5, peerEval4));
    peerEval4.setWeek(4);
    peerEvalRepository.save(peerEval4);

    PeerEvaluation peerEval5 = new PeerEvaluation();
    peerEvalRepository.save(peerEval5);
    peerEval5.setEvaluator(s5);
    peerEval5.setEvaluations(generateEvals(s2, s3, s4, s1, peerEval5));
    peerEval5.setWeek(4);
    peerEvalRepository.save(peerEval5);

    // -----------------------//
    // Foreign Key Assignment //
    // -----------------------//
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
    // TODO: UC19, only team has instructor ID, instructor obj does not link to team
//    i1.setTeams();
//    i1.addTeam();

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6));
    teamRepository.saveAll(Arrays.asList(team1, team2, team3));
    sectionRepository.saveAll(Arrays.asList(sec1, sec2));

    // --------------//
    // Test WAR Data //
    // --------------//

    List<Submission> submissions = new ArrayList<>();

    Submission sub1 = new Submission();
    sub1.setTeamMember(s1);
    sub1.setTaskCategory("Backend dev");
    sub1.setPlannedTask("Task1");
    sub1.setDescription("description of task");
    sub1.setPlannedHours(5.0);
    sub1.setActualHours(6.0);
    sub1.setStatus("Done");
    submissions.add(sub1);

    Submission sub2 = new Submission();
    sub2.setTeamMember(s1);
    sub2.setTaskCategory("Documentation");
    sub2.setPlannedTask("Task2");
    sub2.setDescription("description of task");
    sub2.setPlannedHours(1.0);
    sub2.setActualHours(0.5);
    sub2.setStatus("Done");
    submissions.add(sub2);

    Submission sub3 = new Submission();
    sub3.setTeamMember(s1);
    sub3.setTaskCategory("Frontend dev");
    sub3.setPlannedTask("Task3");
    sub3.setDescription("description of task");
    sub3.setPlannedHours(4.0);
    sub3.setActualHours(8.0);
    sub3.setStatus("Not Done");
    submissions.add(sub3);

    Submission sub4 = new Submission();
    sub4.setTeamMember(s2);
    sub4.setTaskCategory("Integration testing");
    sub4.setPlannedTask("Task4");
    sub4.setDescription("description of task");
    sub4.setPlannedHours(4.0);
    sub4.setActualHours(4.0);
    sub4.setStatus("Done");
    submissions.add(sub4);

    Submission sub5 = new Submission();
    sub5.setTeamMember(s2);
    sub5.setTaskCategory("Documentation");
    sub5.setPlannedTask("Task5");
    sub5.setDescription("description of task");
    submissions.add(sub5);

    Submission sub6 = new Submission();
    sub6.setTeamMember(s2);
    sub6.setTaskCategory("Deployment");
    sub6.setPlannedTask("Task6");
    sub6.setDescription("description of task");
    submissions.add(sub6);
    
    WAR war1 = new WAR();
    war1.setSubmissions(submissions);
    war1.setTeam(team1);
    war1.setWeek(4);

    this.warService.saveWar(war1);

  }

  private List<Evaluation> generateEvals(Student s1, Student s2, Student s3, Student s4, PeerEvaluation peerEval) {
    List<Student> students = new ArrayList<>();
    students.add(s1);
    students.add(s2);
    students.add(s3);
    students.add(s4);
    System.out.println();
    List<Evaluation> evals = new ArrayList<>();
    String[] comments = { "Good Job!", "Needs Improvement", "Keep It Up", "Amazing!", "You may wanna change majors" };
    Random rand = new Random();
    for (int i = 0; i < 4; i++) {

      List<Integer> scores = new ArrayList<>();

      for (int j = 0; j < 6; j++) {
        int randomNumber = 1 + rand.nextInt(10);
        scores.add(randomNumber);
      }

      Evaluation eval = new Evaluation();
      eval.setEvaluated(students.get(i));
      eval.setPeerEvaluation(peerEval);
      eval.setScores(scores);
      eval.setPrivateComments(comments[rand.nextInt(5)]);
      eval.setPublicComments(comments[rand.nextInt(5)]);
      evals.add(eval);
    }
    return evals;
  }

}
