package pl.edu.kopalniakodu.todoapp.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.kopalniakodu.todoapp.domain.Auditable;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    private List<Task> taskList;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Before
    public void setUp() throws Exception {

        Schedule schedule = new Schedule();
        schedule.setPlan("planA");


        taskList = Arrays.asList(
                new Task("testTask_1", "Desc_1", true, TaskWeight.IMPORTANT),
                new Task("testTask_2", "Desc_2", false, TaskWeight.NOT_IMPORTANT),
                new Task("testTask_3", "Desc_3", true, TaskWeight.NOT_IMPORTANT),
                new Task("testTask_4", "Desc_4", false, TaskWeight.MD_IMPORTANT),
                new Task("testTask_5", "Desc_5", true, TaskWeight.IMPORTANT),
                new Task("testTask_6", "Desc_6", false, TaskWeight.MD_IMPORTANT),
                new Task("testTask_7", "Desc_6", true, TaskWeight.MD_IMPORTANT),
                new Task("testTask_8", "Desc_6", false, TaskWeight.MD_IMPORTANT)
        );


        for (Task task : taskList) {
            task.setSchedule(schedule);
            entityManager.persist(task);
        }
        entityManager.flush();
    }


    @Test
    public void updatedTaskHasChangedTitleAndDescriptionAndTaskWeight() {

        //given
        Task task = new Task("testTask", "Don't forget about dentist next monday", true, TaskWeight.NOT_IMPORTANT);
        entityManager.persist(task);
        entityManager.flush();

        String newTitle = "newTitle";
        String newDesc = "newDesc";
        TaskWeight newTaskWeight = TaskWeight.IMPORTANT;

        //when
        taskRepository.updateTask(newTitle, newDesc, newTaskWeight, task.getId());

        //then
        Task foundTask = taskRepository.findById(task.getId()).get();

        assertThat(foundTask.getTitle()).isEqualTo(newTitle);
        assertThat(foundTask.getDescription()).isEqualTo(newDesc);
        assertThat(foundTask.getTaskWeight()).isEqualTo(newTaskWeight);

    }

    @Test
    public void NotFoundTasksWhenAnyPlanMatch() {
        List<Task> foundTasks = taskRepository.findAllTasksByPlan("NOT_EXISTING_PLAN");
        assertThat(foundTasks.size()).isEqualTo(0);

    }

    @Test
    public void FoundTasksWhenPlanMatch() {

        String existingTaskPlan = taskRepository.findAll().get(0).getSchedule().getPlan();
        List<Task> foundTasks = taskRepository.findAllTasksByPlan(existingTaskPlan);

        assertThat(foundTasks.size()).isGreaterThan(0);
    }

    @Test
    public void findByActiveFalseOrderByLastModifiedDate() {

        String existingTaskPlan = taskRepository.findAll().get(0).getSchedule().getPlan();
        List<Task> allTasks = taskRepository.findAll();
        List<Task> foundTasks = taskRepository.findByActiveFalseOrderByLastModifiedDate(existingTaskPlan);

        Collections.shuffle(allTasks);
        Collections.sort(allTasks, Comparator.comparing(Auditable::getLastModifiedDate));
        List<Task> sortedTasks = new ArrayList<>();
        allTasks.forEach(task -> {
            if (!task.getActive()) {
                sortedTasks.add(task);
            }
        });

        boolean isEqual = sortedTasks.equals(foundTasks);
        assertThat(isEqual).isTrue();
        for (Task foundTask : foundTasks) {
            assertThat(foundTask.getActive()).isFalse();
        }
    }


    @Test
    public void findAllTasksSortedByActiveAndTaskWeightAndCreationDate() {

        String existingTaskPlan = taskRepository.findAll().get(0).getSchedule().getPlan();
        List<Task> allTasks = taskRepository.findAll();
        List<Task> foundTasks = taskRepository.findAllTasksSortedByActiveAndTaskWeightAndCreationDate(existingTaskPlan);

        Collections.shuffle(allTasks);
        Collections.sort(allTasks, (o1, o2) -> {

            TaskWeight x1 = o1.getTaskWeight();
            TaskWeight x2 = o2.getTaskWeight();
            int taskWeightComp = x1.compareTo(x2);

            if (taskWeightComp != 0) {
                return taskWeightComp;
            }

            LocalDateTime x3 = o1.getCreationDate();
            LocalDateTime x4 = o2.getCreationDate();
            return x3.compareTo(x4);
        });


        List<Task> sortedTasks = new ArrayList<>();
        allTasks.forEach(task -> {
            if (task.getActive()) {
                sortedTasks.add(task);
            }
        });

        boolean isEqual = sortedTasks.equals(foundTasks);
        assertThat(isEqual).isTrue();
        for (Task foundTask : foundTasks) {
            assertThat(foundTask.getActive()).isTrue();
        }
    }
}