package com.restful.restful;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.restful.restful.Dao.TodoDao;
import com.restful.restful.Service.TodoService;
import com.restful.restful.Entity.Todo;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TestTodoService {
	@Autowired
	TodoService todoService;

	@MockBean
	TodoDao todoDao;

	@Test
	public void testGetTodos () {
    // [Arrange]
    List<Todo> expectedTodosList = new ArrayList<Todo>();
    Todo todo = new Todo();
    todo.setId(1);
    todo.setTask("玩電腦");
    todo.setStatus(1);
    expectedTodosList.add(todo);

    // 模擬回傳結果 
    Mockito.when(todoDao.findAll()).thenReturn(expectedTodosList);

    // [Act]
    Iterable<Todo> actualTodoList = todoService.getTodos();

    // [Assert]
    assertEquals(expectedTodosList, actualTodoList);
  }

	@Test
	public void testCreateTodo () {
		// [Arrange]
		Todo todo = new Todo();
		todo.setId(1);
		todo.setTask("玩電腦");
		todo.setStatus(1);

		// 模擬回傳結果
		Mockito.when(todoDao.save(todo)).thenReturn(todo);

		// [Act]
		Integer actualId = todoService.createTodo(todo);

		// [Assert]
		assertEquals(1, actualId);
	}

	@Test
	public void testUpdateTodoSuccess() {
		// [Arrange]
		Todo todo = new Todo();
		todo.setId(1);
		todo.setTask("玩電腦");
		todo.setStatus(1);
		Optional<Todo> resTodo = Optional.of(todo);

		// 模擬回傳結果
		Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

		// [Act]
		Boolean actualTodo = todoService.updateTodo(1, todo);

		// [Assert]
		assertEquals(true, actualTodo);
	}

	@Test
	public void testUpdateTodoNotExist() {
		// [Arrange]
		Todo todo = new Todo();
		todo.setStatus(2);
		Optional<Todo> resTodo = Optional.of(todo);

		// 模擬回傳結果 (資料庫沒有 id=150 的資料，故回傳 empty 物件)
		Mockito.when(todoDao.findById(150)).thenReturn(Optional.empty());

		// [Act]
		Boolean actualTodo = todoService.updateTodo(150, todo);

		// [Assert]
		assertEquals(false, actualTodo);
	}

	@Test
	public void testUpdateTodoOccurException () {
		// [Arrange]
		Todo todo = new Todo();
		todo.setId(1);
		todo.setStatus(1);
		Optional<Todo> resTodo = Optional.of(todo);

		// 模擬回傳結果 (資料庫裡的 id=1 資料)
		Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
		todo.setStatus(2);

		// 模擬發生 NullPointerException
		doThrow(NullPointerException.class).when(todoDao).save(todo);
		
		// [Act]
		Boolean actualTodo = todoService.updateTodo(2, todo);

		// [Assert]
		assertEquals(false, actualTodo);
	}
}
