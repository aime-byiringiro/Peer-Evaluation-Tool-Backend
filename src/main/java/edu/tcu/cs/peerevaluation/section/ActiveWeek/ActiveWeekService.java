package edu.tcu.cs.peerevaluation.section.ActiveWeek;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ActiveWeekService {

    private  final ActiveWeekRepository activeWeekRepository;


    public ActiveWeekService(ActiveWeekRepository activeWeekRepository) {
        this.activeWeekRepository = activeWeekRepository;
    }
    public ActiveWeek save(ActiveWeek newActiveWeek){
        return  this.activeWeekRepository.save(newActiveWeek);
    }
}
