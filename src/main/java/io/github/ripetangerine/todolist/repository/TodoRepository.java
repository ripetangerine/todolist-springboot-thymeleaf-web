package io.github.ripetangerine.todolist.repository;

import io.github.ripetangerine.todolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TodoRepository
        extends JpaRepository<Todo, Long> {
}