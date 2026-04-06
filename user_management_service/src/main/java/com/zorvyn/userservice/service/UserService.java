package com.zorvyn.userservice.service;

import java.util.List;

import com.zorvyn.userservice.dto.CreateUserRequest;
import com.zorvyn.userservice.dto.CreateUserResponse;
import com.zorvyn.userservice.dto.LoginRequest;
import com.zorvyn.userservice.dto.LoginResponse;
import com.zorvyn.userservice.dto.UpdateRoleRequest;
import com.zorvyn.userservice.dto.UpdateRoleResponse;
import com.zorvyn.userservice.dto.UpdateStatusRequest;
import com.zorvyn.userservice.dto.UpdateStatusResponse;

public interface UserService {
	//create user
	public CreateUserResponse createUser(CreateUserRequest request);
	
	// update role
	public UpdateRoleResponse updateRole(Long userId, UpdateRoleRequest request);

	// update status 
	public UpdateStatusResponse updateStatus(Long userId, UpdateStatusRequest request);
	
	// get all user	
	public List<CreateUserResponse> getAllUsers();

	// get user by id
	public CreateUserResponse userById(Long userId);
	
	//login user
	public LoginResponse loginUser(LoginRequest request);
}
