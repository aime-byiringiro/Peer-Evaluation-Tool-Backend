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
      s1.setId("1");
      s1.setFirstName("Aliya");
      s1.setMiddleInitial("S");
      s1.setLastName("Suri");
      s1.setEmail("aliya.suri@tcu.edu");
      s1.setPassword("summer2024!");

    Student s2 = new Student();
      s2.setId("2");
      s2.setFirstName("James");
      s2.setMiddleInitial("R");
      s2.setLastName("Edmonson");
      s2.setEmail("james.edmonson@tcu.edu");
      s2.setPassword("pizzaLover!2");

    Student s3 = new Student();
      s3.setId("3");
      s3.setFirstName("John");
      s3.setMiddleInitial("P");
      s3.setLastName("Smith");
      s3.setEmail("john.smith@tcu.edu");
      s2.setPassword("helloSunshine!");

    Student s4 = new Student();
      s4.setId("4");
      s4.setFirstName("John");
      s4.setMiddleInitial("B");
      s4.setLastName("Doe");
      s4.setEmail("john.doe@tcu.edu");
      s2.setPassword("coffee4Me:)");

    Student s5 = new Student();
      s5.setId("5");
      s5.setFirstName("Aaron");
      s5.setMiddleInitial("D");
      s5.setLastName("Smith");
      s5.setEmail("aaron.smith@tcu.edu");
      s2.setPassword("bookWorm123!");

    studentRepository.save(s1);
    studentRepository.save(s2);
    studentRepository.save(s3);
    studentRepository.save(s4);
    studentRepository.save(s5);
  }


}
