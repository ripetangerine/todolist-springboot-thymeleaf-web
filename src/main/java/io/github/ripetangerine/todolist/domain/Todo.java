package io.github.ripetangerine.todolist.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private boolean completed;

    public Todo(String title) {
        this.title = title;
    }
}
