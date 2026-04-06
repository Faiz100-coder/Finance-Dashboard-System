package com.zorvyn.userservice.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	USER_NOT_FOUND("4001", "User not found, Please Enter a valid Id"),
	ROLE_NOT_NULL("4002", " Role scannot be Null"),
	GENERIC_ERROR("4003", "SOMETHING WENT WRONG!.."), 
	EMAIL_NOT_FOUND("4004", "Email Not Found"),
	RESOURCE_NOT_FOUND("4005", "Invalid url or Resource Not Found"),
	DUPLICATE_EMAIL("4006", "Email Already Exists.. Try Another Email.."),
	FIELD_VALIDATION_ERROR("4007","Email or Passwored is Missing, check the fields and try again."),
	INVALID_EMAIL_PASS("4008", "Invalid Email or Password");

	private String errorCode;
	private String errorMessage;

	private ErrorCodeEnum(String errorCode, String errorMessage) {

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
