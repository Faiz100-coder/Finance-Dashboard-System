package com.zorvyn.userservice.dto;

import com.zorvyn.userservice.enums.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRoleRequest {
	 @NotNull(message = "Role is required")
	private Role role;

}
