package com.project.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {
	private String title;
	private String isbn;
	private String image;
	private int price;
}
