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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.service.TaskService;

import javax.validation.Valid;

@Controller()
@SessionAttributes("task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    // example url http://localhost:8080/?plan=abc
    @GetMapping(value = {
            "app{plan}",
    })
    public String taskList(@RequestParam(value = "plan") String plan, Model model) {
        model.addAttribute("tasks", taskService.findTasks(plan));
        model.addAttribute("plan", plan);
        return "index";
    }

    // example url http://localhost:8080/history/?plan=abc
    @GetMapping("history{plan}")
    public String taskHistory(@RequestParam(value = "plan") String plan, Model model) {
        model.addAttribute("tasks", taskService.findHistory(plan));
        model.addAttribute("plan", plan);
        return "history";
    }


    @PostMapping("/done")
    public String doneTask(@RequestParam("taskId") Long id, @RequestParam("plan") String plan, RedirectAttributes redirectAttributes) {
        taskService.doneById(id);
        redirectAttributes.addAttribute("plan", plan);
        return "redirect:/app?plan={plan}";
    }


    @PostMapping("/delete")
    public String deleteTask(@RequestParam("taskId") Long id, @RequestParam("plan") String plan, RedirectAttributes redirectAttributes) {
        taskService.deleteById(id);
        redirectAttributes.addAttribute("plan", plan);
        return "redirect:/app?plan={plan}";
    }

    @GetMapping("/add")
    public String newTaskForm(Model model, @RequestParam("plan") String plan) {
        model.addAttribute("task", new Task());
        model.addAttribute("plan", plan);
        return "addTask";
    }


    @PostMapping("/add")
    public String processCreateTask(@RequestParam("plan") String plan, @Valid Task task, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("task", task);
            model.addAttribute("plan", plan);
            return "addTask";
        } else if (task.getId() != null) {
            // updateTaskQuery
            taskService.updateTask(task.getTitle(), task.getDescription(), task.getTaskWeight(), task.getId());
            redirectAttributes.addAttribute("plan", plan);
            return "redirect:/app?plan={plan}";
        } else {

            taskService.createTask(task, plan);
            redirectAttributes.addAttribute("plan", plan);
            return "redirect:/app?plan={plan}";
        }

    }


    @PostMapping("/update")
    public String editTaskForm(@RequestParam("taskId") Long id, Model model, @RequestParam("plan") String plan) {
        Task task = taskService.findById(id);
        model.addAttribute("task", task);
        model.addAttribute("plan", plan);
        return "editTask";
    }


}
