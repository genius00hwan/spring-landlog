package com.landvibe.landlog.exception;

public class BlogException extends RuntimeException {
	ErrorMessages errorMessage;

	public BlogException(ErrorMessages errorMessage) {
		this.errorMessage = errorMessage;
	}

}
