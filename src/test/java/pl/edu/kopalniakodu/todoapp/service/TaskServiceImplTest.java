package pl.edu.kopalniakodu.todoapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;
import pl.edu.kopalniakodu.todoapp.repository.ScheduleRepository;
import pl.edu.kopalniakodu.todoapp.repository.TaskRepository;
import pl.edu.kopalniakodu.todoapp.service.serviceImpl.TaskServiceImpl;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskServiceImplTest {

    TaskServiceImpl taskService;


    @Mock
    TaskRepository taskRepository;

    @Mock
    ScheduleRepository scheduleRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskServiceImpl(taskRepository, scheduleRepository);

    }


    @Test
    public void doneById() {

        Task task = new Task("testTask", "Don't forget about dentist next monday", true, TaskWeight.NOT_IMPORTANT);
        task.setId(1L);

        Mockito.when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        taskService.doneById(task.getId());

        assertThat(task.getActive()).isFalse();
        verify(taskRepository, times(1)).findById(task.getId());
        verify(taskRepository, times(1)).save(task);
    }


    @Test
    public void createTask() {

        Task task = new Task("testTask", "Don't forget about dentist next monday", false, TaskWeight.NOT_IMPORTANT);

        Schedule schedule = new Schedule();
        schedule.setPlan("planA");

        Mockito.when(scheduleRepository.findScheduleByPlan(schedule.getPlan())).thenReturn(schedule);

        taskService.createTask(task, schedule.getPlan());

        assertThat(task.getActive()).isTrue();
        verify(scheduleRepository, times(1)).findScheduleByPlan(schedule.getPlan());
        verify(taskRepository, times(1)).save(task);


    }


}