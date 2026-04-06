package com.zorvyn.userservice.dto;

import com.zorvyn.userservice.enums.Status;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusResponse {
	 
	private Status status;
	 private String message;
	 
		public UpdateStatusResponse(){
			 this.message="Status Updated Successfully..";
			 
		 }

}
