package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class LoginForm {
	private String email;
	private String password;

	public LoginForm(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
