package pl.edu.kopalniakodu.todoapp.service.impl;


import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;
import pl.edu.kopalniakodu.todoapp.repository.ScheduleRepository;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;
import pl.edu.kopalniakodu.todoapp.service.TaskService;
import pl.edu.kopalniakodu.todoapp.service.impl.utill.ExportTasks;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ScheduleRepository scheduleRepository) {
        this.taskRepository = taskRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }


    public List<Task> findTasks(String plan) {
        return taskRepository.findAllTasksSortedByActiveAndTaskWeightAndCreationDate(plan);
    }

    public List<Task> findHistory(String plan) {
        return taskRepository.findByActiveFalseOrderByLastModifiedDate(plan);
    }

    public void doneById(Long id) {
        Task task = taskRepository.findById(id).get();
        task.setActive(false);
        taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(String newTitle, String newDescription, TaskWeight newTaskWeight, Long id) {
        this.taskRepository.updateTask(newTitle, newDescription, newTaskWeight, id);
    }

    public void createTask(Task task, String plan) {
        // set task to be active because only activated tasks are visible on main page.
        task.setActive(true);

        task.setSchedule(scheduleRepository.findScheduleByPlan(plan));
        taskRepository.save(task);
    }

    public Pair<String, Workbook> exportTask(String plan) {
        ExportTasks exportTasks = new ExportTasks();
        return exportTasks.exportFile(taskRepository.findAllTasksByPlan(plan));

    }


}
