package edu.tcu.cs.peerevaluation.ActiveWeek.week;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends JpaRepository<Week,Integer> {



}
