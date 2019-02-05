package pl.edu.kopalniakodu.todoapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;
import pl.edu.kopalniakodu.todoapp.repository.ScheduleRepository;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private TaskRepository taskRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, TaskRepository taskRepository) {
        this.scheduleRepository = scheduleRepository;
        this.taskRepository = taskRepository;
    }


    public void save(Schedule schedule) {

        scheduleRepository.save(schedule);
        schedule.generateRandomPlan();


        // *** EXAMPLE DATA INIT ***
        List<Task> taskList = new ArrayList<>();

        taskList.add(new Task("Ruby programming to jest to", "Learn jUnit5, spring, hibernate Learn jUnit5, spring, hibernate" +
                "Learn jUnit5, spring, hibernate" +
                "Learn jUnit5, spring, hibernate hibernate hibernate", false, TaskWeight.IMPORTANT));
        taskList.add(new Task("Exam", "Learn for exam which u have next Friday", true, TaskWeight.MD_IMPORTANT));
        taskList.add(new Task("Shopping list", "Buy water, bread, ham", true, TaskWeight.NOT_IMPORTANT));
        taskList.add(new Task("Ruby programming", "Learn ruby on rails!", true, TaskWeight.IMPORTANT));
        taskList.add(new Task("Csharp", "Download visual studio code!", true, TaskWeight.MD_IMPORTANT));
        taskList.add(new Task("Phone dad", "Call dad and remind him about meeting", true, TaskWeight.NOT_IMPORTANT));
        taskList.add(new Task("SQL", "Write db for your project", true, TaskWeight.IMPORTANT));
        taskList.add(new Task("Car", "remember to tank car! ", true, TaskWeight.MD_IMPORTANT));
        taskList.add(new Task("Dentist", "Don't forget about dentist next monday", true, TaskWeight.NOT_IMPORTANT));


        scheduleRepository.save(schedule);
        for (Task task : taskList) {
            task.setSchedule(schedule);
            taskRepository.save(task);
        }
        // *** EXAMPLE DATA INIT ***


    }
}
