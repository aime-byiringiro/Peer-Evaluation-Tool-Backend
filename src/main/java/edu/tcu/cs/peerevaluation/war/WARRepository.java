package edu.tcu.cs.peerevaluation.war;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WARRepository extends JpaRepository<WAR,Integer> {

}
