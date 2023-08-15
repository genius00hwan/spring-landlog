package com.landvibe.landlog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class LandlogControllerAdvice {

	@ExceptionHandler({MemberException.class})
	public ResponseEntity<ErrorMessages> handle(MemberException e) {
		log.error(e.errorMessages.getErrorMessage());
		return new ResponseEntity<>(e.errorMessages.getHttpStatus());
	}

	@ExceptionHandler(BlogException.class)
	public ResponseEntity<ErrorMessages> handle(BlogException e) {
		log.error(e.errorMessage.getErrorMessage());
		return new ResponseEntity<>(e.errorMessage.getHttpStatus());
	}
}
