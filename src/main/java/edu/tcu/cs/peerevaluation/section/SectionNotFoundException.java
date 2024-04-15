package edu.tcu.cs.peerevaluation.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(Integer    sectionID) {
        super("Could not find section with Id " + sectionID + " :(");
    }

}
