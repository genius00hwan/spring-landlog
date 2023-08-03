package com.landvibe.landlog.constants;

public enum ErrorMessages {
	NO_MEMBER("존재하지 않는 회원입니다."),
	NO_NAME("이름을 입력해주세요."),

	NO_EMAIL("이메일을 입력해주세요."),
	INVALID_EMAIL("잘못된 이메일입니다."),
	DUPLICATE_EMAIL("중복된 이메일입니다."),

	INVALID_PASSWORD("잘못된 비밀번호입니다."),
	NO_PASSWORD("비밀번호를 입력해주세요."),


	NO_BLOG("존재하지 않는 블로그입니다."),
	NO_TITLE("제목을 입력해주세요."),
	NO_CONTENTS("내용을 입력해주세요.");
	private final String errorMessage;

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String get() {
		return errorMessage;
	}
}
