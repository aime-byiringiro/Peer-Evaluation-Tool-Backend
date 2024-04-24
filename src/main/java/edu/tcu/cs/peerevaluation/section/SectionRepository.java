package edu.tcu.cs.peerevaluation.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer>, JpaSpecificationExecutor<Section>{

    Optional<Section> findBySectionName (String sectionName);

}