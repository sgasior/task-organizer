package pl.edu.kopalniakodu.todoapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
    @Size(min = 1, max = 35, message = "Title can have  between 1 and 30 characters.")
    private String title;

    @NonNull
    @Size(max = 200, message = "Description can have maximum 200 characters.")
    private String description;


    @NonNull
    private Boolean active;

    @NonNull
    @Enumerated(EnumType.STRING)
    private TaskWeight taskWeight;


}










