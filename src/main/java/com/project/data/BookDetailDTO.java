package com.project.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDetailDTO {
	private String title;
	private String author;
	private String publisher;
	private String image;
	private String isbn;
	private String description;
	private int price;
}
