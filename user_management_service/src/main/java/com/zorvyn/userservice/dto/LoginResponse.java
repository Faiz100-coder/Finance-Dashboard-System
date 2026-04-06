package com.zorvyn.userservice.dto;

import lombok.Data;

@Data
//@Builder
public class LoginResponse {

	private String token;
	private String type;
	
	public LoginResponse(String token) {
		this.token = token;
		this.type = "Bearer";
	
	}
}
