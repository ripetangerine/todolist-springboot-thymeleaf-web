package io.github.ripetangerine.todolist.controller;

import io.github.ripetangerine.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping()
    public String todos() {
//
//        model.addAttribute(
//                "todos",
//                todoService.findAll()
//        );

        return "todo/list";
    }

    @GetMapping("/new")
    public String createForm() {
        return "todo/create";
    }

    @PostMapping()
    public String create(String title) {

        todoService.create(title);

        return "redirect:/todos";
    }
}

