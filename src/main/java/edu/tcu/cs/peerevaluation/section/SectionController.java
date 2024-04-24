package edu.tcu.cs.peerevaluation.section;

import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import edu.tcu.cs.peerevaluation.peerEvalUser.MyUserPrincipal;
import edu.tcu.cs.peerevaluation.peerEvalUser.PeerEvalUser;
import edu.tcu.cs.peerevaluation.peerEvalUser.UserRepository;
import edu.tcu.cs.peerevaluation.section.converter.SectionDtoToSectionConverter;
import edu.tcu.cs.peerevaluation.section.converter.SectionToSectionDtoConverter;
import edu.tcu.cs.peerevaluation.section.dto.SectionDto;
import edu.tcu.cs.peerevaluation.student.Student;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;


import java.sql.Statement;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/section")
public class SectionController {
    private final SectionService sectionService;
    private final SectionDtoToSectionConverter sectionDtoToSectionConverter;
    private final SectionToSectionDtoConverter  sectionToSectionDtoConverter;
    private final UserRepository userRepository;



    public SectionController(SectionService sectionService, SectionDtoToSectionConverter sectionDtoToSectionConverter, SectionToSectionDtoConverter sectionToSectionDtoConverter, UserRepository userRepository) {
        this.sectionService = sectionService;
        this.sectionDtoToSectionConverter = sectionDtoToSectionConverter;
        this.sectionToSectionDtoConverter = sectionToSectionDtoConverter;
        this.userRepository = userRepository;
    }




    @PostMapping("/section_search")
    public Result findSectionByCriteria(@RequestBody Map <String, String> searchCriteria , Pageable pageable) {
        Page<Section> sectionPage = this.sectionService.findByCriteria(searchCriteria, pageable);
        Page<SectionDto> sectionDtoPage = sectionPage.map(this.sectionToSectionDtoConverter::convert);

        if (sectionPage.getContent().isEmpty()) {
            return new Result(true, StatusCode.SUCCESS, " Couldn't find this section");
        }
        else {
            return new Result(true, StatusCode.SUCCESS, "Search Success", sectionDtoPage.getContent());
        }



    }

    @GetMapping("{sectionID}")
    public Result findSectionBySectionID(@PathVariable Integer sectionID) {
        Section foundSection = this.sectionService.findBySectionId(sectionID);
        SectionDto sectionDto = this.sectionToSectionDtoConverter.convert(foundSection); // convert the json section into section object
        return new Result(true, StatusCode.SUCCESS, "Find Success", sectionDto);

    }

    @PostMapping
    public Result createNewSection(@RequestBody SectionDto sectionDto) {
        Section newSection = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section savedSection = this.sectionService.save(newSection);
        SectionDto savedSectionDto = this.sectionToSectionDtoConverter.convert(savedSection);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedSectionDto);

    }
    @PutMapping("{sectionID}")
    public Result editSection(@PathVariable Integer sectionID, @Valid @RequestBody SectionDto sectionDto){
        Section edit = this.sectionDtoToSectionConverter.convert(sectionDto);
        Section  editedSection = this.sectionService.edit(sectionID, edit);
        SectionDto editSectionDto = this.sectionToSectionDtoConverter.convert(editedSection);
        return new Result(true, StatusCode.SUCCESS, "Edit Success", editSectionDto);
    }

    //current week method
    @GetMapping("/week")
    public Result getCurrentWeekById(){
        Section currentSection = getLoggedInStudent().getTeam().getSection();
        String currentWeek = currentSection.getCurrentWeek();
        /*
         * TODO
         * if say 5 weeks and 4 days have passed, this will return
         * a 5, so im adding 1 to get the current week
         */
        return new Result(true, StatusCode.SUCCESS, "Edit Success", currentWeek);
    }

    /*
    * Method that retrives the current logged in student
    * object regardless of the authentication method
    * used.
    */
    private Student getLoggedInStudent() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        return principal.getPeerEvalUser().getStudent();
        } else {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext()
            .getAuthentication();
        Jwt jwt = (Jwt) authenticationToken.getCredentials();
        PeerEvalUser user = this.userRepository.findByUsername(jwt.getSubject())
            .orElseThrow(() -> new UsernameNotFoundException("username " + jwt.getSubject() + " is not found."));
        return user.getStudent();
        }
    }
}