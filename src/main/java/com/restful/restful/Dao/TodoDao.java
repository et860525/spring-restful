package com.restful.restful.Dao;

import com.restful.restful.Entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo, Integer> {
	
}
