package edu.tcu.cs.peerevaluation.ActiveWeek.week;

import edu.tcu.cs.peerevaluation.rubric.criterion.Criterion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Criterion,Integer> {

}
