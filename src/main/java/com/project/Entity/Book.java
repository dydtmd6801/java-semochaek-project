package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "books")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long book_id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	@Column(nullable = false)
	private String publisher;
	
	@Column(nullable = false, unique = true)
	private String isbn;
	
	@Column(nullable = false)
	private String image;
	
	@Column(nullable = false, length = 1000)
	private String description;
	
	@Column(nullable = false)
	private int price;
	
	private String genreLv1;
	private String genreLv2;
	private String genreLv3;
}
