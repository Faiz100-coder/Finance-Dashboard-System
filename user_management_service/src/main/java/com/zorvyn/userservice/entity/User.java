package com.zorvyn.userservice.entity;


import com.zorvyn.userservice.enums.Role;
import com.zorvyn.userservice.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name="user")
@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name; 
	

	@Column(unique= true, nullable = false)
	private String email; 
	

	@Column(nullable = false)
	private String password; 
	

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role; 
	

	 @Enumerated(EnumType.STRING)
	 @Column(nullable = false)
	 private Status status;

}
