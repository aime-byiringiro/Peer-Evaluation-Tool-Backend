package edu.tcu.cs.peerevaluation.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(String    sectionName) {
        super("Could not find section with Id " + sectionName + " :(");
    }

}
