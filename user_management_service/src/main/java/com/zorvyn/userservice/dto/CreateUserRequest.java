package com.zorvyn.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class CreateUserRequest {
	   
		@NotBlank(message= "Name is Required")
	    private String name;

	    @Email(message = "Email format is invalid")
	    @NotBlank(message= "Email is Required")
	    private String email;

	    @NotBlank(message= "Password is Required")
	    @Size(min = 6, message = "Password must be at least 6 characters")
	    private String password;

}
