package com.zorvyn.userservice.dto;

import com.zorvyn.userservice.enums.Role;
import com.zorvyn.userservice.enums.Status;

import lombok.Data;

@Data
public class CreateUserResponse {
	  private Long id;
	    private String name;
	    private String email;
	    private Role role;
	    private Status status;

}
