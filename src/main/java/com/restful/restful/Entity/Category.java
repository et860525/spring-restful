package com.restful.restful.Entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	public String name;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	@EqualsAndHashCode.Exclude
	private Set<Todo> todos;
}
