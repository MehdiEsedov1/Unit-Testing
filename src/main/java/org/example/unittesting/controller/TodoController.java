package org.example.unittesting.controller;

import lombok.RequiredArgsConstructor;
import org.example.unittesting.model.Todo;
import org.example.unittesting.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    @GetMapping("/findAll")
    public List<Todo> getAllTodos() {
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    public Todo findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/save")
    public Todo save(@RequestBody Todo todo) {
        return service.save(todo);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
