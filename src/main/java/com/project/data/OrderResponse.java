package com.project.data;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
	private String merchantId;
	private int amount;
	private Date paidAt;
	private List<DetailOrderResponse> detailOrders;
}
