package edu.tcu.cs.peerevaluation.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.PeerEvaluationRepostitory;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.EvaluationRepository;
import edu.tcu.cs.peerevaluation.rubric.Rubric;
import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;

@Component
public class DBDataInitializer implements CommandLineRunner{

  private final StudentRepository studentRepository;
  private final PeerEvaluationRepostitory peerEvalRepository;
  private final EvaluationRepository evalRepository;

  public DBDataInitializer(StudentRepository studentRepository, PeerEvaluationRepostitory peerEvalRepository, EvaluationRepository evalRepository) {
    this.studentRepository = studentRepository;
    this.peerEvalRepository = peerEvalRepository;
    this.evalRepository = evalRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    //-------------------//
    // Test Student Data //
    //-------------------//

    Student s1 = new Student();
      s1.setId("S1");
      s1.setFirstName("Aliya");
      s1.setMiddleInitial("S");
      s1.setLastName("Suri");
      s1.setEmail("aliya.suri@tcu.edu");
      s1.setPassword("summer2024!");

    Student s2 = new Student();
      s2.setId("S2");
      s2.setFirstName("James");
      s2.setMiddleInitial("R");
      s2.setLastName("Edmonson");
      s2.setEmail("james.edmonson@tcu.edu");
      s2.setPassword("pizzaLover!2");

    Student s3 = new Student();
      s3.setId("S3");
      s3.setFirstName("John");
      s3.setMiddleInitial("P");
      s3.setLastName("Smith");
      s3.setEmail("john.smith@tcu.edu");
      s3.setPassword("helloSunshine!");

    Student s4 = new Student();
      s4.setId("S4");
      s4.setFirstName("John");
      s4.setMiddleInitial("B");
      s4.setLastName("Doe");
      s4.setEmail("john.doe@tcu.edu");
      s4.setPassword("coffee4Me:)");

    Student s5 = new Student();
      s5.setId("S5");
      s5.setFirstName("Aaron");
      s5.setMiddleInitial("D");
      s5.setLastName("Smith");
      s5.setEmail("aaron.smith@tcu.edu");
      s5.setPassword("bookWorm123!");

    studentRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5));

    
    //---------------------//
    // Test Criterion Data //
    //---------------------//
    List<Criterion> criterionList = new ArrayList();

  
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


    //------------------//
    // Test Rubric Data //
    //------------------//

    Rubric r1 = new Rubric();
      r1.setRubricName("2024 Rubric");
      r1.setCriterionList(criterionList);

    //-------------------//
    // Test Section Data //
    //-------------------//

    //----------------//
    // Test Team Data //
    //----------------//

    //----------------------//
    // Test Instructor Data //
    //----------------------//

    //-----------------//
    // Test Admin Data //
    //-----------------//

    //---------------------------//
    // Test Peer Evaluation Data //
    //---------------------------//
    
    PeerEvaluation peerEval1 = new PeerEvaluation();
      peerEval1.setId("1001");
      peerEval1.setEvaluator(s1);
      peerEval1.setEvaluations(generateEvals(s2,s3,s4,s5));
      peerEval1.setWeek("week 4");
      peerEvalRepository.save(peerEval1);
 
    PeerEvaluation peerEval2 = new PeerEvaluation();
      peerEval2.setId("1002");
      peerEval2.setEvaluator(s2);
      peerEval2.setEvaluations(generateEvals(s1,s3,s4,s5));
      peerEval2.setWeek("week 4");
      peerEvalRepository.save(peerEval2);

    PeerEvaluation peerEval3 = new PeerEvaluation();
      peerEval3.setId("1003");
      peerEval3.setEvaluator(s3);
      peerEval3.setEvaluations(generateEvals(s2,s1,s4,s5));
      peerEval3.setWeek("week 4");
      peerEvalRepository.save(peerEval3);

    PeerEvaluation peerEval4 = new PeerEvaluation();
      peerEval4.setId("1004");
      peerEval4.setEvaluator(s4);
      peerEval4.setEvaluations(generateEvals(s2,s3,s1,s5));
      peerEval4.setWeek("week 4");
      peerEvalRepository.save(peerEval4);

    PeerEvaluation peerEval5 = new PeerEvaluation();
      peerEval5.setId("1005");
      peerEval5.setEvaluator(s5);
      peerEval5.setEvaluations(generateEvals(s2,s3,s4,s1));
      peerEval5.setWeek("week 4");
      peerEvalRepository.save(peerEval5);
  }

  private List<Evaluation> generateEvals(Student s1,Student s2,Student s3,Student s4) {
    List<Student> students = new ArrayList<>();
    students.add(s1);
    students.add(s2);
    students.add(s3);
    students.add(s4);
    System.out.println();
    List<Evaluation> evals = new ArrayList<>();
    String[] comments = {"Good Job!", "Needs Improvement", "Keep It Up", "Amazing!", "You may wanna change majors"};
    Random rand = new Random();
    int id = 1;
    for (int i=0; i<4; i++){
      
      List<Integer> scores = new ArrayList<>();

      for (int j=0; j<6; j++) {
        int randomNumber = 1 + rand.nextInt(10);
        scores.add(randomNumber);
      }

      Evaluation eval = new Evaluation();
      //eval.setId(id + "");
      eval.setEvaluated(students.get(i));
      eval.setScores(scores);
      eval.setPrivateComments(comments[rand.nextInt(5)]);
      eval.setPublicComments(comments[rand.nextInt(5)]);
      evalRepository.save(eval);
      evals.add(eval);
      id++;
      }
    return evals;
  }
 

}
