package com.zorvyn.financeservice.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	RECORD_NOT_FOUND("5001", "Record Not Found.!, Please Enter a valid Id"),
	ROLE_NOT_NULL("5002", " Role scannot be Null"),
	GENERIC_ERROR("5003", "SOMETHING WENT WRONG!.."), 
	EMAIL_NOT_FOUND("5004", "Email Not Found"),
	RESOURCE_NOT_FOUND("5005", "Invalid url or Resource Not Found"),
	AMOUNT_VALIDATION_ERROR("5006", "Amount must be greater than 0");

	private String errorCode;
	private String errorMessage;

	private ErrorCodeEnum(String errorCode, String errorMessage) {

		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
}
