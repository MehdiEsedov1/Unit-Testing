# 🧪 Integration Testing in Spring Boot using `MockMvc`

This markdown file explains integration testing in Spring Boot, using `MockMvc`, `@SpringBootTest`, and `@AutoConfigureMockMvc`. The context is based on the test class `TodoControllerIntegrationTest`.

---

## 📌 What is Integration Testing?

Integration testing verifies that multiple units or layers of an application (e.g., controllers, services, and repositories) work together as expected. Unlike unit testing, integration tests often include:

- Full Spring ApplicationContext
- Actual interaction with the database (e.g., H2)
- HTTP request simulation
- Real bean wiring (not mocks)

---

## ✅ Goal of `TodoControllerIntegrationTest`

To test the REST endpoints exposed by the `TodoController`, and ensure the end-to-end functionality of:

- Creating a Todo
- Retrieving a Todo
- Deleting a Todo

All these operations are tested with actual service and repository logic.

---

## 📦 Annotations Explained

### `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`

- Loads the full Spring Boot application context.
- Simulates the application running on a real server.
- `RANDOM_PORT` tells Spring Boot to assign a random available port, which prevents port conflicts during testing.

### `@AutoConfigureMockMvc`

- Injects a `MockMvc` bean into the test context.
- `MockMvc` is used to send mock HTTP requests (GET, POST, DELETE) to controller endpoints.
- It avoids starting a real embedded server like Tomcat.

---

## 🧪 Test Breakdown

### `testAddTodo_thenGetAll`

- Sends a POST request to `/todos/save` with a new Todo object in JSON format.
- Verifies the response is HTTP 200 OK.
- Sends a GET request to `/todos/findAll` and checks that the response is JSON and successful.

### `testFindById_whenTodoExists_returnsTodo`

- Creates a new Todo by sending a POST request.
- Extracts the ID of the newly saved Todo from the response.
- Sends a GET request to `/todos/findById/{id}` using that ID.
- Verifies the returned JSON contains the correct title and completed status.

### `testDeleteById_whenTodoExists_thenSuccess`

- Sends a POST request to save a Todo.
- Extracts the Todo ID from the response.
- Sends a DELETE request to `/todos/deleteById/{id}` using the extracted ID.
- Verifies the delete operation returns HTTP 200 OK.

---

## 📋 Autowired Components

### `MockMvc`

- A class provided by Spring Test for HTTP request simulation.
- Used for sending requests to controller endpoints inside tests.
- Does not require the application to be deployed.

### `ObjectMapper`

- Converts Java objects to JSON (serialization).
- Converts JSON to Java objects (deserialization).
- Essential for preparing request bodies and processing responses.

---

## 🆚 Integration Test vs Unit Test

| Feature                | Unit Test                       | Integration Test                          |
|------------------------|----------------------------------|--------------------------------------------|
| Spring Context         | Not loaded                      | Fully loaded                               |
| Performance            | Very fast                       | Slower due to full app context             |
| Scope                  | One class or method             | Multiple layers (controller → repo)        |
| HTTP Simulation        | Not applicable                  | Possible using MockMvc                     |
| Real DB Used           | No (use mocks)                  | Yes (H2 or Testcontainers)                 |
| Focus                  | Business logic in isolation     | Layer interaction and full behavior        |

---

## 🛠️ Best Practices

- Use in-memory databases (like H2) for isolation and speed.
- Keep integration tests independent — don’t share state.
- Roll back changes using `@Transactional` if needed.
- Use clear naming like `TodoControllerIntegrationTest` for clarity.

---

## ✅ Summary

This integration test suite validates that the REST API behaves as expected by simulating real HTTP requests through `MockMvc`. It:

- Loads the full Spring context.
- Performs real database operations.
- Exercises the complete controller-service-repository stack.
- Uses annotations like `@SpringBootTest` and `@AutoConfigureMockMvc` for setup.

Integration testing bridges the gap between isolated unit testing and real-world usage, ensuring that your application behaves correctly across all layers.
