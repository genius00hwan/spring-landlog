package com.landvibe.landlog.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorMessages {
	NO_MEMBER_MESSAGE(NOT_FOUND, "존재하지 않는 회원입니다."),
	NO_NAME_MESSAGE(BAD_REQUEST, "이름을 입력해주세요."),
	INVALID_EMAIL_MESSAGE(BAD_REQUEST, "잘못된 이메일입니다."),
	NO_EMAIL_MESSAGE(BAD_REQUEST, "이메일을 입력해 주세요"),
	DUPLICATE_EMAIL_MESSAGE(CONFLICT, "중복된 이메일입니다."),
	INVALID_PASSWORD_MESSAGE(UNAUTHORIZED, "잘못된 비밀번호입니다."),
	NO_PASSWORD_MESSAGE(BAD_REQUEST, "비밀번호를 입력해주세요."),

	INVALID_BLOG_ID_MESSAGE(NOT_FOUND, "블로그 ID 오류!"),
	NO_BLOG_MESSAGE(NOT_FOUND, "존재하지 않는 블로그입니다."),
	NO_TITLE_MESSAGE(BAD_REQUEST, "제목을 입력해주세요."),
	NO_CONTENTS_MESSAGE(BAD_REQUEST, "내용을 입력해주세요.");
	private final String errorMessage;
	private final HttpStatus httpStatus;

	ErrorMessages(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}
}
