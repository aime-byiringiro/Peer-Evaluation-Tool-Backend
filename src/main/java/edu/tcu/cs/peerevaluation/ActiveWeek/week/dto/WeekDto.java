package edu.tcu.cs.peerevaluation.ActiveWeek.week.dto;

import jakarta.persistence.criteria.CriteriaBuilder;

public record WeekDto (Integer id,
                      String weekName,
                      String startDate){

}
