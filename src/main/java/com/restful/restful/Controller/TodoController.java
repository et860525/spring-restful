package com.restful.restful.Controller;

import com.restful.restful.Entity.Todo;

import jakarta.annotation.PostConstruct;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class TodoController {
	private final List<Todo> todoDB = new ArrayList<>();

	@PostConstruct
	private void initDB() {
		todoDB.add(new Todo("1", "Watch video"));
		todoDB.add(new Todo("2", "Play the ball"));
		todoDB.add(new Todo("3", "Sleep"));
		todoDB.add(new Todo("4", "Eat"));
		todoDB.add(new Todo("5", "Learn Java"));
	}

	@GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") String id) {
			Optional<Todo> todoOp = todoDB.stream()
			  .filter(todo -> todo.getId().equals(id))
				.findFirst();
			
			if (todoOp.isPresent()) {
				Todo todo = todoOp.get();
			  return ResponseEntity.ok().body(todo);
			} else {
				return ResponseEntity.notFound().build();
			}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> getTodos() {
		List<Todo> todoOp = todoDB;

		return ResponseEntity.ok().body(todoOp);
	}

	@PostMapping("/todos")
	public ResponseEntity<Todo> createProduct(@RequestBody Todo request) {
    boolean isIdDuplicated = todoDB.stream()
            .anyMatch(p -> p.getId().equals(request.getId()));
    if (isIdDuplicated) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    Todo todo = new Todo();
    todo.setId(request.getId());
    todo.setTask(request.getTask());
    todoDB.add(todo);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(todo.getId())
            .toUri();

    return ResponseEntity.created(location).body(todo);
	}
}
