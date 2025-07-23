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
        todo.setCompleted(true);

        String json = objectMapper.writeValueAsString(todo);

        // Save Todo
        String response = mockMvc.perform(post("/todos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Deserialize to get ID
        Todo savedTodo = objectMapper.readValue(response, Todo.class);
        Long id = savedTodo.getId();

        // Fetch by ID
        mockMvc.perform(get("/todos/findById/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Test GET by ID"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testDeleteById_whenTodoExists_thenSuccess() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Delete this");
        todo.setCompleted(false);

        String json = objectMapper.writeValueAsString(todo);

        // Save
        String response = mockMvc.perform(post("/todos/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo savedTodo = objectMapper.readValue(response, Todo.class);
        Long id = savedTodo.getId();

        // Delete
        mockMvc.perform(delete("/todos/deleteById/" + id))
                .andExpect(status().isOk());
    }
}
