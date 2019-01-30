package pl.edu.kopalniakodu.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;

import javax.validation.Valid;

@Controller
@SessionAttributes("task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @GetMapping({"/", "index.html", "index"})
    public String taskList(Model model) {

        //model.addAttribute("tasks", taskRepository.findByActiveTrue());
        model.addAttribute("tasks", taskRepository.findAllTasksSortedByActiveAndTaskWeightAndCreationDate());
        return "index";
    }


    @GetMapping("/history")
    public String taskHistory(Model model) {
        model.addAttribute("tasks", taskRepository.findByActiveFalseOrderByLastModifiedDate());
        return "history";
    }


    @PostMapping("/done")
    public String doneTask(@RequestParam("taskId") Long id) {
        Task task = taskRepository.findById(id).get();
        task.setActive(false);
        taskRepository.save(task);
        return "redirect:/";
    }


    @PostMapping("/delete")
    public String deleteTask(@RequestParam("taskId") Long id) {
        Task task = taskRepository.findById(id).get();
        taskRepository.delete(task);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "addTask";
    }

    @PostMapping("/add")
    public String processCreateTask(@Valid Task task, BindingResult bindingResult, Model model) {
        logger.info("task in processCreateTask: " + task);
        if (bindingResult.hasErrors()) {
            model.addAttribute("task", task);
            return "addTask";
        } else if (task.getId() != null) {
            // updateTaskQuery
            taskRepository.updateTask(task.getTitle(), task.getDescription(), task.getTaskWeight(), task.getId());
            return "redirect:/";
        } else {

            // set task to be active because only activated tasks are visible on main page.
            task.setActive(true);

            // saving to the db
            taskRepository.save(task);
            return "redirect:/";
        }

    }


    @PostMapping("/update")
    public String editTaskForm(@RequestParam("taskId") Long id, Model model) {
        Task task = taskRepository.findById(id).get();
        model.addAttribute("task", task);
        return "editTask";
    }


}
