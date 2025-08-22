package com.project.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
	String email;
	String name;
	String telephone;
}
