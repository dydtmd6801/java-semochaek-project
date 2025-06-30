package com.project.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailOrderResponse {
	private String bookIsbn;
	private String title;
	private int quantity;
	private int price;
}
