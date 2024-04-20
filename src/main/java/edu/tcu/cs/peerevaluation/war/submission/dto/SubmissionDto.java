package edu.tcu.cs.peerevaluation.war.submission.dto;

public record SubmissionDto(Integer id,
                            String teamMember,
                            String taskCategory,
                            String plannedTask,
                            String description,
                            Double plannedHours,
                            Double actualHours,
                            String status) {

}
