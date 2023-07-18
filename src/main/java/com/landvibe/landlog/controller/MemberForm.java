package com.landvibe.landlog.controller;

public class MemberForm {
	private String name;
	private String password;
	private String email;

	public MemberForm(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
