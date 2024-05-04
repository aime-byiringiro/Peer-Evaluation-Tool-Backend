package edu.tcu.cs.peerevaluation.ActiveWeek;





public class ActiveWeekService {

    private  final ActiveWeekRepository activeWeekRepository;


    public ActiveWeekService(ActiveWeekRepository activeWeekRepository) {
        this.activeWeekRepository = activeWeekRepository;
    }
    public ActiveWeek save(ActiveWeek newActiveWeek){
        return  this.activeWeekRepository.save(newActiveWeek);
    }
}
