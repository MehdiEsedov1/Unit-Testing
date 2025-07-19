package org.example.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.unittesting.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TodoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddTodo_thenGetAll() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Learn Integration Test");
        todo.setCompleted(false);

        mockMvc.perform(post("/todos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/todos/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById_whenTodoExists_returnsTodo() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Test GET by ID");
        todo.setCompleted(false);

        String json = objectMapper.writeValueAsString(todo);

        mockMvc.perform(post("/todos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/todos/findById/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test GET by ID"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testFindById_whenTodoNotExists_returns404() throws Exception {
        mockMvc.perform(get("/todos/findById/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteById_whenTodoExists_thenSuccess() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Delete this");
        todo.setCompleted(false);

        String json = objectMapper.writeValueAsString(todo);

        mockMvc.perform(post("/todos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/todos/deleteById/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById_whenTodoNotExists_returns404() throws Exception {
        mockMvc.perform(delete("/todos/findById/9999"))
                .andExpect(status().is4xxClientError());
    }
}
