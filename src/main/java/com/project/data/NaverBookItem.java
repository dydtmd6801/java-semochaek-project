package com.project.data;

import lombok.Data;

@Data
public class NaverBookItem {
	private String title;
	private String author;
	private String publisher;
	private String isbn;
	private String image;
	private String description;
	private int price;
}
