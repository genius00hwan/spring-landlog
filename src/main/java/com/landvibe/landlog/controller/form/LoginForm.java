package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginForm {
	private final String email;
	private final String password;

	public LoginForm(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
