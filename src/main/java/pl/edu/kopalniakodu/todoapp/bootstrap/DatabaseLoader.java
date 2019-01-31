package pl.edu.kopalniakodu.todoapp.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;
import pl.edu.kopalniakodu.todoapp.repository.ScheduleRepository;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

//import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;

@Component
public class DatabaseLoader implements CommandLineRunner {


    private TaskRepository taskRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public DatabaseLoader(TaskRepository taskRepository, ScheduleRepository scheduleRepository) {
        this.taskRepository = taskRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        System.out.println("In run method");
        List<Task> taskList = new ArrayList<>();

        taskList.add(new Task("Ruby programming to jest to", "Learn jUnit5, spring, hibernate Learn jUnit5, spring, hibernate" +
                "Learn jUnit5, spring, hibernate" +
                "Learn jUnit5, spring, hibernate hibernate hibernate", false, TaskWeight.IMPORTANT));
        taskList.add(new Task("Exam", "Learn for exam which u have next Friday", true, TaskWeight.MD_IMPORANT));
        taskList.add(new Task("Shopping list", "Buy water, bread, ham", true, TaskWeight.NOT_IMPORTANT));
        taskList.add(new Task("Ruby programming", "Learn ruby on rails!", true, TaskWeight.IMPORTANT));
        taskList.add(new Task("Csharp", "Download visual studio code!", true, TaskWeight.MD_IMPORANT));
        taskList.add(new Task("Phone dad", "Call dad and remind him about meeting", true, TaskWeight.NOT_IMPORTANT));
        taskList.add(new Task("SQL", "Write db for your project", true, TaskWeight.IMPORTANT));
        taskList.add(new Task("Car", "remember to tank car! ", true, TaskWeight.MD_IMPORANT));
        taskList.add(new Task("Dentist", "Don't forget about dentist next monday", true, TaskWeight.NOT_IMPORTANT));


        Schedule schedule = new Schedule();
        scheduleRepository.save(schedule);
        for (Task task : taskList) {
            task.setSchedule(schedule);
            taskRepository.save(task);
        }

        Schedule schedule1 = new Schedule();
        schedule1.setPlan("aaa");
        Task task10 = new Task("testujemy", "Don't forget about dentist next monday", true, TaskWeight.NOT_IMPORTANT);
        task10.setSchedule(schedule1);
        taskRepository.save(task10);

    }
}
