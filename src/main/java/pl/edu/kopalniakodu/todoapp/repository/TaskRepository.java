package pl.edu.kopalniakodu.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.kopalniakodu.todoapp.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
