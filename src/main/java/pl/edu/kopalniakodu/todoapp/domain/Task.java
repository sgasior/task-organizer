package pl.edu.kopalniakodu.todoapp.domain;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {


    private Long id;

    private String title;

    private String description;

    private boolean active;


}
