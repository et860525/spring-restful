package com.restful.restful;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.restful.restful.Entity.Todo;
import com.restful.restful.Service.TodoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class TestTodoController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	TodoService todoService;

	@Test
	public void testGetTodos() throws Exception {
		// [Arrange]
		List<Todo> expectedList = new ArrayList<Todo>();
		Todo mockTodo = new Todo();
		mockTodo.setId(1);
		mockTodo.setTask("看書");
		mockTodo.setStatus(1);

		expectedList.add(mockTodo);

		// 模擬回傳結果
		Mockito.when(todoService.getTodos()).thenReturn(expectedList);

		// 存放請求標頭的資料
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		// [Act] + [Assert] 發出請求並驗證
		mockMvc.perform(get("/api/todos")
		.headers(httpHeaders))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id").hasJsonPath())
			.andExpect(jsonPath("$[0].task").value(mockTodo.getTask()))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	public void testDeleteTodoSuccess() throws Exception {
		// 模擬回傳結果
		Mockito.when(todoService.deleteTodo(1)).thenReturn(true);

		// 存放請求標頭的資料
		RequestBuilder requestBuilder =
				MockMvcRequestBuilders
						.delete("/api/todos/1")
						.accept(MediaType.APPLICATION_JSON) // response 的型別
						.contentType(MediaType.APPLICATION_JSON); // request 的型別

		// 模擬呼叫
		mockMvc.perform(requestBuilder)
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testDeleteTodoIdNotExist() throws Exception {
		// 模擬回傳結果
		Mockito.when(todoService.deleteTodo(100)).thenReturn(false);

		// 存放請求標頭的資料
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		// 模擬呼叫
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/100").headers(httpHeaders))
			.andDo(print())
			.andExpect(status().isBadRequest()); 
	}
}
