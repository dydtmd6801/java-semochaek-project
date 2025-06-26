package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Setter
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String telephone;
	
	@Column(nullable = false)
	private String role;
	
	@Setter
	@Column(nullable = false, length = 1)
	private String status;
	
	@Column(nullable = true)
	private String address;
	
	@Column(nullable = true)
	private String detailAddress;
	
	@Column(nullable = true)
	private Integer postNumber;

}
