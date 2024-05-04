package edu.tcu.cs.peerevaluation.section.ActiveWeek;

import edu.tcu.cs.peerevaluation.section.ActiveWeek.converter.ActiveWeekDtoToActiveWeekConverter;
import edu.tcu.cs.peerevaluation.section.ActiveWeek.converter.ActiveWeekToActiveWeekDtoConverter;
import edu.tcu.cs.peerevaluation.section.ActiveWeek.dto.ActiveWeekDto;
import edu.tcu.cs.peerevaluation.system.Result;
import edu.tcu.cs.peerevaluation.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activeWeek")
public class ActiveWeekController {

    private  final ActiveWeekDtoToActiveWeekConverter activeWeekDtoToActiveWeekConverter;
    private final ActiveWeekToActiveWeekDtoConverter activeWeekToActiveWeekDtoConverter;
    private final ActiveWeekService activeWeekService;

    public ActiveWeekController(ActiveWeekDtoToActiveWeekConverter activeWeekDtoToActiveWeekConverter,
                                ActiveWeekToActiveWeekDtoConverter activeWeekToActiveWeekDtoConverter,
                                ActiveWeekService activeWeekService) {
        this.activeWeekDtoToActiveWeekConverter = activeWeekDtoToActiveWeekConverter;
        this.activeWeekToActiveWeekDtoConverter = activeWeekToActiveWeekDtoConverter;
        this.activeWeekService = activeWeekService;
    }

    @PostMapping
    public Result newActiveWeek(@Valid @RequestBody ActiveWeekDto activeWeekDto){
        ActiveWeek newActiveWeek = this.activeWeekDtoToActiveWeekConverter.convert(activeWeekDto);
        ActiveWeek savedActiveWeek = this.activeWeekService.save(newActiveWeek);
        ActiveWeekDto savedDto = this.activeWeekToActiveWeekDtoConverter.convert(savedActiveWeek);
        return  new Result(true, StatusCode.SUCCESS, "add success", savedDto);

    }
}
