package edu.tcu.cs.peerevaluation.student.dto;

//import jakarta.validation.constraints.NotEmpty;
//this allows the '@NotEmpty modifier, but requires a dependency'

public record StudentDto(String id,
                         String firstName,
                         String lastName,
                         String email) {

}
