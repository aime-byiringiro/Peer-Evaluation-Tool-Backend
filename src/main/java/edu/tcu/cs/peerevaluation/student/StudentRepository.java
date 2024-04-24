package edu.tcu.cs.peerevaluation.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student>{

    // Method to find all students by their section name
    @Query("SELECT s FROM Student s WHERE s.team.section.sectionName = :sectionName")
    List<Student> findAllBySectionName(String sectionName);
    
}
