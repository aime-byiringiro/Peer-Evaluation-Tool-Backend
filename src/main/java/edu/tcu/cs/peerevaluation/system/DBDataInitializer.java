package edu.tcu.cs.peerevaluation.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.tcu.cs.peerevaluation.student.Student;
import edu.tcu.cs.peerevaluation.student.StudentRepository;

@Component
public class DBDataInitializer implements CommandLineRunner{

  private final StudentRepository studentRepository;


  public DBDataInitializer(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Student s1 = new Student();
      s1.setId("1250808601744904191");
      s1.setFirstName("Aliya");
      s1.setLastName("Suri");
      s1.setEmail("aliya.suri@tcu.edu");

    Student s2 = new Student();
      s2.setId("1250808601744904192");
      s2.setFirstName("James");
      s2.setLastName("Edmonson");
      s2.setEmail("james.edmonson@tcu.edu");

    studentRepository.save(s1);
    studentRepository.save(s2);
  }


}
