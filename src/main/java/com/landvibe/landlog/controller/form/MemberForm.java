package com.landvibe.landlog.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberForm {
	private String name;
	private String password;
	private String email;

	public MemberForm(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
}
