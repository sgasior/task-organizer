package pl.edu.kopalniakodu.todoapp.service;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;

import java.util.List;

public interface TaskService {
    Task findById(Long id);

    List<Task> findTasks(String plan);

    List<Task> findHistory(String plan);

    void doneById(Long id);

    void deleteById(Long id);

    void updateTask(String newTitle, String newDescription, TaskWeight newTaskWeight, Long id);

    void createTask(Task task, String plan);

    Pair<String, Workbook> exportTask(String plan);
}
