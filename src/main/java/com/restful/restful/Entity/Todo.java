package com.restful.restful.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	String task = "";

	@Column(insertable = false, columnDefinition = "int default 1")
	Integer status = 1;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	Date createTime = new Date();

	@LastModifiedDate
	@Column(nullable = false)
	Date updateTime = new Date();

	@JsonBackReference
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="category_id")
	private Category category;
}
