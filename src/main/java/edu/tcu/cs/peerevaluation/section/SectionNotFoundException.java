package edu.tcu.cs.peerevaluation.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(String sectionId) {
        super("Could not find section with Id " + sectionId + " :(");
    }

}
