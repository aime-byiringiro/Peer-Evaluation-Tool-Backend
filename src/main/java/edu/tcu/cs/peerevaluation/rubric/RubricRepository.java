package edu.tcu.cs.peerevaluation.rubric;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricRepository extends JpaRepository<Rubric, Integer>{

}
