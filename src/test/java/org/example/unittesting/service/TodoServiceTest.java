package org.example.unittesting.service;

import lombok.RequiredArgsConstructor;
import org.example.unittesting.model.Todo;
import org.example.unittesting.repository.TodoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
public class TodoServiceTest {
    @Mock
    private TodoRepo repo;

    @InjectMocks
    private TodoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_returnsTodoList() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test 2");
        todo2.setCompleted(true);

        List<Todo> todos = List.of(
                todo1, todo2
        );

        when(repo.findAll()).thenReturn(todos);

        List<Todo> result = service.findAll();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindById_whenExists_returnsTodo() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Find me");

        when(repo.findById(1L)).thenReturn(Optional.of(todo));

        Todo result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Find me", result.getTitle());
        verify(repo).findById(1L);
    }

    @Test
    void testFindById_whenNotExists_throwsException() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.findById(1L);
        });
    }

    @Test
    void testSave_callsRepositorySave() {
        Todo todo = new Todo();
        todo.setTitle("New Todo");

        service.save(todo);

        verify(repo).save(todo);
    }

    @Test
    void testDeleteById_callsRepositoryDeleteById() {
        when(repo.existsById(1L)).thenReturn(true);

        service.deleteById(1L);

        verify(repo).deleteById(1L);
    }
}
