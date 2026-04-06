package com.zorvyn.userservice.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ProjectException.class)
	public ResponseEntity<ErrorResponse> authExceptionHandler(ProjectException ex) {
		
		
		
		ErrorResponse  message = new ErrorResponse(
				ex.getErrorCode(),
				ex.getErrorMessage()
				);
		
		log.info("error handled ");
		
		return new ResponseEntity<>(message, ex.getHttpStatus());
		
	
		
	}	  // dto validation 
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
	        MethodArgumentNotValidException ex) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .get(0)
	            .getDefaultMessage();

	    return new ResponseEntity<>(
	            new ErrorResponse("4000", message),
	            HttpStatus.BAD_REQUEST
	    );
	}

	
//	   url/ params validation error 
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
		
		String message = ex.getConstraintViolations().iterator().next().getMessage();
		
		ErrorResponse response= new ErrorResponse("4000", message);	
		log.info("error handled ");
		
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
				
				
	}
	// resource not found excepiton 
	@ExceptionHandler(NoHandlerFoundException.class)
	
	public ResponseEntity<ErrorResponse> handleNotFount(NoHandlerFoundException ex) {
		
		log.info("error handle 404 url not found ");
		
		ErrorResponse response = new ErrorResponse(
				ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(),
				ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorMessage());
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		
	}
	// for invalid enum values in request	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormat(HttpMessageNotReadableException ex) {

	    Throwable cause = ex.getCause();

	    if (cause instanceof InvalidFormatException invalidFormat) {
	        // Check if it's an enum field
	        if (invalidFormat.getTargetType().isEnum()) {
	            String fieldName = invalidFormat.getPath().get(0).getFieldName();
	            String invalidValue = invalidFormat.getValue().toString();
	            String acceptedValues = Arrays.stream(invalidFormat.getTargetType().getEnumConstants())
	                    .map(Object::toString)
	                    .collect(Collectors.joining(", "));

	            return new ResponseEntity<>(
	                    new ErrorResponse("4000",
	                    fieldName + " : '" + invalidValue + "' is invalid. Accepted values are: " + acceptedValues),
	                    HttpStatus.BAD_REQUEST
	            );
	        }
	    }

	    return new ResponseEntity<>(
	            new ErrorResponse("4000", "Invalid request body"),
	            HttpStatus.BAD_REQUEST
	    );
	}
	
	
	// handle generic exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> genericExceptionHandler(Exception ex) throws Exception{
		
		 // VERY IMPORTANT: let Spring Security handle auth errors
	    if (ex instanceof org.springframework.security.access.AccessDeniedException
	        || ex instanceof org.springframework.security.core.AuthenticationException) {
	        throw ex;
	    }

		
		ErrorResponse response = new ErrorResponse(
				"5000",
				"generic : some went wrong");
		
		log.info(" generic error handled {} ",response);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				
		
	}
	
	

}
