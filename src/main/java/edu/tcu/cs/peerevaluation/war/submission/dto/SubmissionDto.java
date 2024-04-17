package edu.tcu.cs.peerevaluation.war.submission.dto;

public record SubmissionDto(Integer id,
                            String teamMember,
                            String taskCategory,
                            String plannedTask,
                            String description,
                            Double plannedHours,
                            Double actualHours,
                            Boolean status) {
                              //TODO, should the boolean of status be turned into a string value

}
