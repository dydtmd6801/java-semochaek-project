package com.project.data;

import lombok.Data;

@Data
public class OrderItemRequest {
	private String bookIsbn;
	private int quantity;
	private int price;
}
