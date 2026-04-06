package com.zorvyn.userservice.dto;

import com.zorvyn.userservice.enums.Role;

import lombok.Data;

@Data
public class UpdateRoleResponse {
	 
	private Role role;
	 private String message;
	 
		public UpdateRoleResponse(){
			 this.message="Role Updated Successfully..";
			 
		 }

}
