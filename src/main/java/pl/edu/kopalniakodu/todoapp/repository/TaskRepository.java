package pl.edu.kopalniakodu.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.kopalniakodu.todoapp.domain.Task;
import pl.edu.kopalniakodu.todoapp.domain.TaskWeight;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findByActiveFalseOrderByLastModifiedDate();

    @Query("SELECT t FROM Task t WHERE t.active=true ORDER BY t.taskWeight ,t.creationDate ASC ")
    List<Task> findAllTasksSortedByActiveAndTaskWeightAndCreationDate();


    //@Query("update Task t set t.title = ?1, t.description= ?2 where t.id = ?3")
    @Modifying
    @Transactional
    @Query("update Task t set t.title=:newTitle, t.description=:newDescription, t.taskWeight=:newTaskWeight WHERE t.id= :id")
    int updateTask(@Param("newTitle") String newTitle,
                   @Param("newDescription") String newDescription,
                   @Param("newTaskWeight") TaskWeight newTaskWeight,
                   @Param("id") Long id);

}
