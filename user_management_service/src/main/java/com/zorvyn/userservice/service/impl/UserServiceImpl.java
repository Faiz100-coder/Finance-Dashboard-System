 package com.zorvyn.userservice.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.zorvyn.userservice.dto.CreateUserRequest;
import com.zorvyn.userservice.dto.CreateUserResponse;
import com.zorvyn.userservice.dto.LoginRequest;
import com.zorvyn.userservice.dto.LoginResponse;
import com.zorvyn.userservice.dto.UpdateRoleRequest;
import com.zorvyn.userservice.dto.UpdateRoleResponse;
import com.zorvyn.userservice.dto.UpdateStatusRequest;
import com.zorvyn.userservice.dto.UpdateStatusResponse;
import com.zorvyn.userservice.entity.User;
import com.zorvyn.userservice.enums.Role;
import com.zorvyn.userservice.enums.Status;
import com.zorvyn.userservice.exceptions.ErrorCodeEnum;
import com.zorvyn.userservice.exceptions.ProjectException;
import com.zorvyn.userservice.jwt.JwtUtil;
import com.zorvyn.userservice.repository.UserRepository;
import com.zorvyn.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final ModelMapper modelMapper;
	
	private final JwtUtil jwtUtil;
	
	private final BCryptPasswordEncoder passwordEncoder;
		
	private final UserRepository userRepository;

	@Override
	public CreateUserResponse createUser(CreateUserRequest request) {
		
		 // Email check before saving
	    if (userRepository.existsByEmail(request.getEmail())) {
	        throw new ProjectException(
	                ErrorCodeEnum.DUPLICATE_EMAIL.getErrorCode(),
	                ErrorCodeEnum.DUPLICATE_EMAIL.getErrorMessage(),
	                HttpStatus.CONFLICT
	        );
	    }

		User user = modelMapper.map(request, User.class);

		user.setRole(Role.VIEWER);
		user.setStatus(Status.ACTIVE);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		User savedUser = userRepository.save(user);

		CreateUserResponse response = modelMapper.map(savedUser, CreateUserResponse.class);
		log.info("user created successfully");
		return response;

	}

	@Override
	public UpdateRoleResponse updateRole(Long userId, UpdateRoleRequest request) {


		// Fetch target user
		User user = userRepository.findById(userId).
				orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage(),
						HttpStatus.NOT_FOUND));

		// Validate role, already handled by @NotNull
		if (request.getRole() == null) {
			throw new ProjectException(
					ErrorCodeEnum.ROLE_NOT_NULL.getErrorCode(),
					ErrorCodeEnum.ROLE_NOT_NULL.getErrorMessage(),
					HttpStatus.BAD_REQUEST);
		}

		// STEP 5: Update role
		user.setRole(request.getRole());

		// STEP 6: Save
		User savedUser = userRepository.save(user);

		// STEP 7: Return response
		return modelMapper.map(savedUser, UpdateRoleResponse.class);
	}

	// update status
	@Override
	public UpdateStatusResponse updateStatus(Long userId, UpdateStatusRequest request) {
		// fetch user
		User user = userRepository.findById(userId).
				orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage(),
						HttpStatus.NOT_FOUND));
		// status null check already handled using @NotNull 
		
		// update status
		user.setStatus(request.getStatus());
		// save
		User savedUser = userRepository.save(user);

		return modelMapper.map(savedUser, UpdateStatusResponse.class);
	}

//	get all data
	@Override
	public List<CreateUserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();

		return  users.stream()
				.map(user -> modelMapper
				.map(user, CreateUserResponse.class))
				.toList();
	}
	// get data by Id

	@Override
	public CreateUserResponse userById(Long userId) {

		User user = userRepository.findById(userId).
				orElseThrow(() -> new ProjectException(
						ErrorCodeEnum.USER_NOT_FOUND.getErrorCode(),
						ErrorCodeEnum.USER_NOT_FOUND.getErrorMessage(),
						HttpStatus.NOT_FOUND));

		return modelMapper.map(user, CreateUserResponse.class);
	}
	
	// LOGIN USER
	
public LoginResponse loginUser(LoginRequest request) {
		
		log.info("login user info {}", request);
		
		// can also done using jakarta validation
		if(request.getEmail()==null || request.getEmail().trim().isEmpty()
				||request.getPassword()==null || request.getPassword().trim().isEmpty()) {

			throw new ProjectException(
					ErrorCodeEnum.FIELD_VALIDATION_ERROR.getErrorCode(),
					ErrorCodeEnum.FIELD_VALIDATION_ERROR.getErrorMessage(),
					HttpStatus.BAD_REQUEST);
		}
		
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(()-> new ProjectException(
					
						ErrorCodeEnum.INVALID_EMAIL_PASS.getErrorCode(),
						ErrorCodeEnum.INVALID_EMAIL_PASS.getErrorMessage(),
						HttpStatus.UNAUTHORIZED
				));
						
				
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			 throw new ProjectException(
				 	ErrorCodeEnum.INVALID_EMAIL_PASS.getErrorCode(),
					ErrorCodeEnum.INVALID_EMAIL_PASS.getErrorMessage(),
					HttpStatus.UNAUTHORIZED
			 );
		}	
		
		
		String token = jwtUtil.generateToken(user.getEmail(),user.getRole().name());
		return new LoginResponse(token);

				
	}
		

}