package pl.edu.kopalniakodu.todoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;
import pl.edu.kopalniakodu.todoapp.service.ScheduleService;

@Controller
public class MainPageController {

    private ScheduleService scheduleService;

    public MainPageController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "mainPage";
    }


    @PostMapping("/addSchedule")
    public String processCreateTask(Model model, RedirectAttributes redirectAttributes) {
        Schedule schedule = new Schedule();
        scheduleService.save(schedule);
        redirectAttributes.addAttribute("plan", schedule.getPlan());
        return "redirect:/app?plan={plan}";
    }

}
