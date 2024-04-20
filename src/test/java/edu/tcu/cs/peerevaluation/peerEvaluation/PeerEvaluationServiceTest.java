package edu.tcu.cs.peerevaluation.peerEvaluation;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.tcu.cs.peerevaluation.peerEvaluation.evaluation.Evaluation;
import edu.tcu.cs.peerevaluation.student.Student;

@ExtendWith(MockitoExtension.class)
public class PeerEvaluationServiceTest {

  @Mock
  PeerEvaluationRepostitory peerEvalRepostitory;

  @InjectMocks
  PeerEvaluationService peerEvalService;

  List<Evaluation> evals;

  @BeforeEach
  void setUp(){
    Student jake = new Student();
    jake.setFirstName("jake");

    Student mark = new Student();
    jake.setFirstName("mark");


    evals = new ArrayList<>();
    Evaluation e1 = new Evaluation();
      e1.setEvaluated(mark);


    
  }

  @AfterEach
  void tearDown(){

  }

  @Test
  void testAddPeerEval() {
    //Given
    Student john = new Student();
    john.setFirstName("john");

    PeerEvaluation peerEval = new PeerEvaluation();
    peerEval.setEvaluator(john);
    

  }



}
