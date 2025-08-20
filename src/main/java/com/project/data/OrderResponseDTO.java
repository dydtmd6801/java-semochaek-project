package com.project.data;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDTO {
	private Long orderId;
	private String merchantId;
	private Date paidAt;
	private int amount;
}
