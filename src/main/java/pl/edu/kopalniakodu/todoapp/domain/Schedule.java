package pl.edu.kopalniakodu.todoapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.edu.kopalniakodu.todoapp.utill.RandomURLGeneratorImpl;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Schedule extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plan;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "schedule", orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();


    public Schedule() {

        //for dev purpose
//        this.plan = "abc";
    }


    public void generateRandomPlan() {
        this.plan = RandomURLGeneratorImpl.generateRandomUrl() + this.getId();
    }

}
