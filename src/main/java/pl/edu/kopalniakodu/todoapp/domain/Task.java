package pl.edu.kopalniakodu.todoapp.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Task extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private boolean active;


}
