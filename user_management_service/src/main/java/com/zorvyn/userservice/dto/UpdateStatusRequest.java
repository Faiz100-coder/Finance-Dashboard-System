package com.zorvyn.userservice.dto;

import com.zorvyn.userservice.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
	 @NotNull(message = "Status cannot be null")
	private Status status;
	

}
