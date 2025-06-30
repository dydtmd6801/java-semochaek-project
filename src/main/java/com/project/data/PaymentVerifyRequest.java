package com.project.data;

import java.util.List;

import lombok.Data;

@Data
public class PaymentVerifyRequest {
	private String impUid;
	private String merchantUid;
	private List<OrderItemRequest> items;
}
