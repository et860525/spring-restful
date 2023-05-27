package com.restful.restful.Controller;

import com.restful.restful.Entity.Todo;
import com.restful.restful.Service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Tag(name="Todo List")
public class TodoController {
	@Autowired
	TodoService todoService;

	@Operation(summary = "Get all todo", description = "You can get all todos")
	@GetMapping("/todos")
	public ResponseEntity<Iterable<Todo>> getTodos() {
		Iterable<Todo> todoList = todoService.getTodos();
		return ResponseEntity.status(HttpStatus.OK).body(todoList);
	}

	@Operation(summary = "Get a todo")
	@GetMapping("/todos/{id}")
	public Optional<Todo> getTodo(@PathVariable Integer id) {
		Optional<Todo> todo = todoService.findById(id);
		return todo;
	}

	@Operation(summary = "Create todo")
	@ApiResponse(responseCode = "201", description = "Create OK")
	@PostMapping("/todos")
	public ResponseEntity<Integer> createTodo(@RequestBody Todo todo) {
		Integer result_todo = todoService.createTodo(todo);
		return ResponseEntity.status(HttpStatus.CREATED).body(result_todo);
	}

	@Operation(summary = "Update todo")
	@PutMapping("/todos/{id}")
	public ResponseEntity<String> updateTodo(@PathVariable Integer id, @RequestBody Todo todo) {
		Boolean result_todo = todoService.updateTodo(id, todo);
		if (!result_todo) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status fields required!");
		}
		return ResponseEntity.status(HttpStatus.OK).body("");
	}

	@Operation(summary = "Delete todo")
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<String> deletTodo(@PathVariable Integer id) {
		Boolean result_todo = todoService.deleteTodo(id);
		if (!result_todo) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not exist");
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
	}
}
