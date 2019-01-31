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
import pl.edu.kopalniakodu.todoapp.repository.ScheduleRepository;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;

import javax.validation.Valid;

@Controller()
@SessionAttributes("task")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskRepository taskRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, ScheduleRepository scheduleRepository) {
        this.taskRepository = taskRepository;
        this.scheduleRepository = scheduleRepository;
    }


    // example url http://localhost:8080/?plan=abc
    @GetMapping(value = {
            "{plan}",
            "{plan}index.html",
            "{plan}index", ""
    })
    public String taskList(@RequestParam(value = "plan") String plan, Model model) {
        //model.addAttribute("tasks", taskRepository.findByActiveTrue());
        model.addAttribute("tasks", taskRepository.findAllTasksSortedByActiveAndTaskWeightAndCreationDate(plan));
        model.addAttribute("plan", plan);
        return "index";
    }

    // example url http://localhost:8080/history/?plan=abc
    @GetMapping("history{plan}")
    public String taskHistory(@RequestParam(value = "plan") String plan, Model model) {
        model.addAttribute("tasks", taskRepository.findByActiveFalseOrderByLastModifiedDate(plan));
        model.addAttribute("plan", plan);
        return "history";
    }


    @PostMapping("/done")
    public String doneTask(@RequestParam("taskId") Long id, @RequestParam("plan") String plan, RedirectAttributes redirectAttributes) {
        Task task = taskRepository.findById(id).get();
        task.setActive(false);
        taskRepository.save(task);
        redirectAttributes.addAttribute("plan", plan);
        return "redirect:/?plan={plan}";
    }


    @PostMapping("/delete")
    public String deleteTask(@RequestParam("taskId") Long id, @RequestParam("plan") String plan, RedirectAttributes redirectAttributes) {
        taskRepository.deleteById(id);
        redirectAttributes.addAttribute("plan", plan);
        return "redirect:/?plan={plan}";
    }

    @GetMapping("/add")
    public String newTaskForm(Model model, @RequestParam("plan") String plan) {
        System.out.println("plan in newTaskForm " + plan);
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
            taskRepository.updateTask(task.getTitle(), task.getDescription(), task.getTaskWeight(), task.getId());
            logger.info("plan is " + plan);
            redirectAttributes.addAttribute("plan", plan);
            return "redirect:/?plan={plan}";
        } else {

            // set task to be active because only activated tasks are visible on main page.
            task.setActive(true);

            // find schedule by plan
            task.setSchedule(scheduleRepository.findScheduleByPlan(plan));

            // saving to the db
            taskRepository.save(task);
            logger.info("plan is " + plan);
            redirectAttributes.addAttribute("plan", plan);
            return "redirect:/?plan={plan}";
        }

    }


    @PostMapping("/update")
    public String editTaskForm(@RequestParam("taskId") Long id, Model model, @RequestParam("plan") String plan) {
        Task task = taskRepository.findById(id).get();
        model.addAttribute("task", task);
        model.addAttribute("plan", plan);
        return "editTask";
    }


}
