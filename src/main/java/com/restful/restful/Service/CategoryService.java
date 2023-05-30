package com.restful.restful.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restful.restful.Dao.CategoryDao;
import com.restful.restful.Entity.Category;

@Service
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;

	public Optional<Category> getTodosByCategoryId(Integer id) {
		Optional<Category> category = categoryDao.findById(id);
		return category;
	}
}
