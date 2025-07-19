package org.example.unittesting.service;

import lombok.RequiredArgsConstructor;
import org.example.unittesting.model.Todo;
import org.example.unittesting.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepo repo;

    public List<Todo> findAll() {
        return repo.findAll();
    }

    public Todo findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }

    public void save(Todo todo) {
        repo.save(todo);
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        repo.deleteById(id);
    }

}
