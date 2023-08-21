package com.landvibe.landlog.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException {

	public ErrorMessages errorMessage;

	public MemberException(ErrorMessages errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String toString() {
		return errorMessage.getHttpStatus() + "(" + errorMessage.getErrorMessage() + ")";
	}

	@Override
	public String getMessage(){
		return errorMessage.getErrorMessage();
	}
}
