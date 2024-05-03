package edu.tcu.cs.peerevaluation.war;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WARRepository extends JpaRepository<WAR, Integer> {

  @Query("SELECT w FROM WAR w WHERE w.team.id = :teamId AND w.week = :week")
  WAR findByWeekAndTeam(Integer teamId, String week);

  @Query("SELECT w FROM WAR w WHERE w.team.teamName = :teamName AND w.week = :week")
  List<WAR> findByWeekAndTeamName(String teamName, String week);

}
