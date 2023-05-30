package com.restful.restful.Dao;

import org.springframework.data.repository.CrudRepository;

import com.restful.restful.Entity.Category;


public interface CategoryDao extends CrudRepository<Category, Integer> {
	
}
