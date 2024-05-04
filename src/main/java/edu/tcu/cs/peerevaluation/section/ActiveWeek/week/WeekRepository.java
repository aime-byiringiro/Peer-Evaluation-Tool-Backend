package edu.tcu.cs.peerevaluation.section.ActiveWeek.week;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends JpaRepository<Week,Integer> {



}
