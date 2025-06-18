package com.project.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {
	private String title;
	private String author;
	private String publisher;
	private String isbn;
	private String image;
	private int price;
}
