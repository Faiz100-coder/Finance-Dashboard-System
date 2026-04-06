package com.zorvyn.userservice.controller;


import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zorvyn.userservice.dto.CreateUserRequest;
import com.zorvyn.userservice.dto.CreateUserResponse;
import com.zorvyn.userservice.dto.LoginRequest;
import com.zorvyn.userservice.dto.LoginResponse;
import com.zorvyn.userservice.dto.UpdateRoleRequest;
import com.zorvyn.userservice.dto.UpdateRoleResponse;
import com.zorvyn.userservice.dto.UpdateStatusRequest;
import com.zorvyn.userservice.dto.UpdateStatusResponse;
import com.zorvyn.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j

public class UserController {

	private final UserService userService;

// I could have return the ResponseEntity for methods for Status codes but its not necessary i focued on business logic
	
	@PostMapping("/users/create")
	public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

		return userService.createUser(request);

	}

	// role update only admin
	
	@PutMapping("user/{userId}/role")
	@PreAuthorize("hasRole('ADMIN')")
	public UpdateRoleResponse updateRole(@PathVariable Long userId, @Valid @RequestBody UpdateRoleRequest request) {

		return userService.updateRole(userId, request);
	}
	

	// change status
	@PutMapping("/user/{userId}/status")
	@PreAuthorize("hasRole('ADMIN')")
	public UpdateStatusResponse updateStatus(@PathVariable Long userId,@Valid @RequestBody UpdateStatusRequest request) {

		UpdateStatusResponse response = userService.updateStatus(userId, request);
		return response;
	}

	// get all users
	@GetMapping("/users")
	//@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	public List<CreateUserResponse> getAllUsers() {

		return userService.getAllUsers();
	}

	// get user by id
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
	public CreateUserResponse userById(@PathVariable Long userId) {

		return userService.userById(userId);

	}
	
	//Login User

	@PostMapping("/login")
	public LoginResponse loginUser(@Valid @RequestBody LoginRequest request) {
		log.info("login user");
		return userService.loginUser(request);
	}
}

	


