package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException{

	ErrorMessages errorMessages;
	public MemberException(ErrorMessages errorMessages){
		this.errorMessages = errorMessages;
	}
}
