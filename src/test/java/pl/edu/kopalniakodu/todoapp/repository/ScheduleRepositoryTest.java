package pl.edu.kopalniakodu.todoapp.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScheduleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScheduleRepository scheduleRepository;


    @Test
    public void whenFindScheduleByPlan_thenReturnSchedule() {

        //given
        Schedule schedule = new Schedule();
        schedule.setPlan("planName");
        entityManager.persist(schedule);
        entityManager.flush();

        //when
        Schedule foundSchedule = scheduleRepository.findScheduleByPlan(schedule.getPlan());

        //then
        assertThat(foundSchedule.getPlan())
                .isEqualTo(schedule.getPlan());

    }



}