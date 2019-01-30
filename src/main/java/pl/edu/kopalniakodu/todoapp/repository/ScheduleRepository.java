package pl.edu.kopalniakodu.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.todoapp.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
