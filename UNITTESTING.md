
# 🧪 Unit Test: TodoServiceTest

This document explains the structure and purpose of the `TodoServiceTest` class, which is a **unit test** for the `TodoService` class. The test ensures the service behaves correctly in isolation using **Mockito** for mocking dependencies.

---

## 📂 Class Overview

### `TodoServiceTest`
Unit tests for the `TodoService` using mocked `TodoRepo`.

---

## 🔍 Annotations Explained

### `@Mock`
- Declares a mock object.
- Used to simulate behavior of dependencies like databases or external services.

### `@InjectMocks`
- Creates an instance of the class under test and injects all the mocks into it.
- Automatically injects the mock `repo` into the `TodoService` instance.

### `@BeforeEach`
- Executes the method before each test case.
- Used to initialize the mocks using `MockitoAnnotations.openMocks(this)`.

---

## ✅ Test Methods Explained

### `testFindAll_returnsTodoList()`
- Mocks `repo.findAll()` to return a list of two todos.
- Calls `service.findAll()` and verifies the result size is 2.
- Uses `verify()` to confirm the method was called once.

### `testFindById_whenExists_returnsTodo()`
- Mocks `repo.findById()` to return a valid `Todo` object.
- Asserts that the returned todo is not null and matches expected values.

### `testFindById_whenNotExists_throwsException()`
- Mocks `repo.findById()` to return `Optional.empty()`.
- Asserts that a `RuntimeException` is thrown.

### `testSave_callsRepositorySave()`
- Calls `service.save(todo)` and verifies `repo.save()` is invoked.

### `testDeleteById_callsRepositoryDeleteById()`
- Mocks `repo.existsById()` to return `true`.
- Verifies that `repo.deleteById()` is called.

---

## 🧠 Purpose of Unit Testing

- Validates individual service logic in isolation.
- Does **not** load Spring context or interact with a real database.
- Fast and lightweight test to catch regressions and logic bugs early.

---

## 🛠 Tools Used

- **JUnit 5** – for writing and executing tests.
- **Mockito** – for mocking the repository dependency.
