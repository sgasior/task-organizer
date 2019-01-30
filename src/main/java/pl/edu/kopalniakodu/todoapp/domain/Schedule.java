package pl.edu.kopalniakodu.todoapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.kopalniakodu.todoapp.utill.RandomURLGeneratorImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Schedule extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String urlParam;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "schedule", orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();


    public Schedule() {
        this.urlParam = RandomURLGeneratorImpl.generateRandomUrl();
    }
}
