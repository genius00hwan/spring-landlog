package com.landvibe.landlog.exception;

public class BlogException extends RuntimeException {
	public ErrorMessages errorMessage;

	public BlogException(ErrorMessages errorMessage) {
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
