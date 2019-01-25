package pl.edu.kopalniakodu.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.todoapp.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByActiveTrue();

    List<Task> findByActiveFalse();

}
