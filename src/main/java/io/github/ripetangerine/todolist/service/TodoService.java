package io.github.ripetangerine.todolist.service;

import io.github.ripetangerine.todolist.domain.Todo;
import io.github.ripetangerine.todolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public void create(String title) {
        Todo todo = new Todo(title);

        todoRepository.save(todo);
    }
}