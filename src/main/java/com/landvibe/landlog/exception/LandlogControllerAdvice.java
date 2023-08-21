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
		log.error(e.toString());
		return new ResponseEntity<>(e.errorMessage.getHttpStatus());
	}

	@ExceptionHandler(BlogException.class)
	public ResponseEntity<ErrorMessages> handle(BlogException e) {
		log.error(e.toString());
		return new ResponseEntity<>(e.errorMessage.getHttpStatus());
	}
}
