package com.zorvyn.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

	@NotNull
	@NotBlank(message = "Email is Required")
    private String email;
	
	@NotNull
	@NotBlank(message = "Password is Required")
    private String password;
}
