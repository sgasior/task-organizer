package pl.edu.kopalniakodu.todoapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Task extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "Please enter a title.")
    private String title;

    @NonNull
    private String description;


    @NonNull
    private Boolean active;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TaskWeight taskWeight;


}










