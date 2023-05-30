package com.restful.restful.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restful.restful.Entity.Category;
import com.restful.restful.Service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories/{id}/todos")
	public ResponseEntity<Optional<Category>> getTodosByCategoryId(@PathVariable Integer id) {
		Optional<Category> category = categoryService.getTodosByCategoryId(id);
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
}
