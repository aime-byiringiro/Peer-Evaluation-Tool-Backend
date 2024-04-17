package edu.tcu.cs.peerevaluation.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.instructor.Instructor;
import edu.tcu.cs.peerevaluation.instructor.InstructorRepository;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluationRepostitory;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.EvaluationRepository;
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


@Component
public class DBDataInitializer implements CommandLineRunner{

  private final StudentRepository studentRepository;
  private final PeerEvaluationRepostitory peerEvalRepository;
  private final EvaluationRepository evalRepository;
  private final InstructorRepository instructorRepository;
  private final RubricRepository rubricRepository;
  private final CriterionRepository criterionRepository;
  private final SectionRepository sectionRepository;
  private final TeamRepository teamRepository;


  public DBDataInitializer(StudentRepository studentRepository, PeerEvaluationRepostitory peerEvalRepository, EvaluationRepository evalRepository, InstructorRepository instructorRepository, RubricRepository rubricRepository, CriterionRepository criterionRepository, SectionRepository sectionRepository, TeamRepository teamRepository) {
    this.studentRepository = studentRepository;
    this.peerEvalRepository = peerEvalRepository;
    this.evalRepository = evalRepository;
    this.instructorRepository = instructorRepository;
    this.rubricRepository = rubricRepository;
    this.criterionRepository = criterionRepository;
    this.sectionRepository = sectionRepository;
    this.teamRepository = teamRepository;
  }

  
  

  @Override
  public void run(String... args) throws Exception {

    //-------------------//
    // Test Student Data //
    //-------------------//

    Student s1 = new Student();
      s1.setFirstName("Aliya");
      s1.setMiddleInitial("S");
      s1.setLastName("Suri");
      s1.setEmail("aliya.suri@tcu.edu");
      s1.setPassword("summer2024!");

    Student s2 = new Student();
      s2.setFirstName("James");
      s2.setMiddleInitial("R");
      s2.setLastName("Edmonson");
      s2.setEmail("james.edmonson@tcu.edu");
      s2.setPassword("pizzaLover!2");

    Student s3 = new Student();
      s3.setFirstName("John");
      s3.setMiddleInitial("P");
      s3.setLastName("Smith");
      s3.setEmail("john.smith@tcu.edu");
      s3.setPassword("helloSunshine!");

    Student s4 = new Student();
      s4.setFirstName("John");
      s4.setMiddleInitial("B");
      s4.setLastName("Doe");
      s4.setEmail("john.doe@tcu.edu");
      s4.setPassword("coffee4Me:)");

    Student s5 = new Student();
      s5.setFirstName("Aaron");
      s5.setMiddleInitial("D");
      s5.setLastName("Smith");
      s5.setEmail("aaron.smith@tcu.edu");
      s5.setPassword("bookWorm123!");

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));

    
    //---------------------//
    // Test Criterion Data //
    //---------------------//
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

    //------------------//
    // Test Rubric Data //
    //------------------//

    Rubric r1 = new Rubric();
      r1.setRubricName("2024 Rubric");
      r1.setCriterionList(criterionList);
    rubricRepository.save(r1);

    //----------------//
    // Test Team Data //
    //----------------//

    Team team1 = new Team();
      team1.setTeamName("Peer Evaluation");
    teamRepository.save(team1);
    Team team2 = new Team();
      team2.setTeamName("Superfrog Scheduler");
    teamRepository.save(team2);

    Team team3 = new Team();
      team3.setTeamName("Moning Meteorite");
    teamRepository.save(team3);
    


    //-------------------//
    // Test Section Data //
    //-------------------//



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





    //----------------------//
    // Test Instructor Data //
    //----------------------//
    Instructor i1 = new Instructor();
      i1.setFirstName("Liran");
      i1.setLastName("Ma");
      i1.setEmail("l.ma@tcu.edu");
    instructorRepository.save(i1);

    //-----------------//
    // Test Admin Data //
    //-----------------//

    //---------------------------//
    // Test Peer Evaluation Data //
    //---------------------------//
    
    PeerEvaluation peerEval1 = new PeerEvaluation();
      peerEvalRepository.save(peerEval1);
      peerEval1.setEvaluator(s1);
      peerEval1.setEvaluations(generateEvals(s2,s3,s4,s5,peerEval1));
      peerEval1.setWeek("week 4");
      peerEvalRepository.save(peerEval1);
 
    PeerEvaluation peerEval2 = new PeerEvaluation();
      peerEvalRepository.save(peerEval2);
      peerEval2.setEvaluator(s2);
      peerEval2.setEvaluations(generateEvals(s1,s3,s4,s5,peerEval2));
      peerEval2.setWeek("week 4");
      peerEvalRepository.save(peerEval2);

    PeerEvaluation peerEval3 = new PeerEvaluation();
      peerEvalRepository.save(peerEval3);
      peerEval3.setEvaluator(s3);
      peerEval3.setEvaluations(generateEvals(s2,s1,s4,s5,peerEval3));
      peerEval3.setWeek("week 4");
      peerEvalRepository.save(peerEval3);

    PeerEvaluation peerEval4 = new PeerEvaluation();
      peerEvalRepository.save(peerEval4);
      peerEval4.setEvaluator(s4);
      peerEval4.setEvaluations(generateEvals(s2,s3,s1,s5,peerEval4));
      peerEval4.setWeek("week 4");
      peerEvalRepository.save(peerEval4);

    PeerEvaluation peerEval5 = new PeerEvaluation();
      peerEvalRepository.save(peerEval5);
      peerEval5.setEvaluator(s5);
      peerEval5.setEvaluations(generateEvals(s2,s3,s4,s1,peerEval5));
      peerEval5.setWeek("week 4");
      peerEvalRepository.save(peerEval5);


    //------------------------//
    // Foreign Key Assignment //
    //------------------------//

    s1.setTeam(team1);
    s2.setTeam(team1);
    s3.setTeam(team2);
    s4.setTeam(team2);
    s5.setTeam(team3);
    team1.setSection(sec1);
    team2.setSection(sec1);
    team3.setSection(sec2);
    team1.setStudents(Arrays.asList(s1,s2));
    team2.setStudents(Arrays.asList(s3,s4));
    team3.setStudents(Arrays.asList(s5));
    sec1.setTeams(Arrays.asList(team1,team2));
    sec2.setTeams(Arrays.asList(team3));

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));
    teamRepository.saveAll(Arrays.asList(team1,team2,team3));
    sectionRepository.saveAll(Arrays.asList(sec1,sec2));

  }

  private List<Evaluation> generateEvals(Student s1,Student s2,Student s3,Student s4, PeerEvaluation peerEval) {
    List<Student> students = new ArrayList<>();
    students.add(s1);
    students.add(s2);
    students.add(s3);
    students.add(s4);
    System.out.println();
    List<Evaluation> evals = new ArrayList<>();
    String[] comments = {"Good Job!", "Needs Improvement", "Keep It Up", "Amazing!", "You may wanna change majors"};
    Random rand = new Random();
    for (int i=0; i<4; i++){
      
      List<Integer> scores = new ArrayList<>();

      for (int j=0; j<6; j++) {
        int randomNumber = 1 + rand.nextInt(10);
        scores.add(randomNumber);
      }

      Evaluation eval = new Evaluation();
      eval.setEvaluated(students.get(i));
      eval.setPeerEvalId(peerEval.getId());
      eval.setScores(scores);
      eval.setPrivateComments(comments[rand.nextInt(5)]);
      eval.setPublicComments(comments[rand.nextInt(5)]);
      evalRepository.save(eval);
      evals.add(eval);
      }
    return evals;
  }
 

}
