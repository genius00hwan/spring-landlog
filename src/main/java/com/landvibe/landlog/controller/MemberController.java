package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping(value = "/new")
	public String createForm() {
		return "members/createMemberForm";
	}

	@PostMapping(value = "/new")
	public String create() {
		return "redirect:/";
	}

	@GetMapping
	public String list() {
		return "members/memberList";
	}

	@GetMapping(value = "/login")
	public String createLogInForm() {
		return "/members/loginForm";
	}

	@PostMapping(value = "/login")
	public String logIn() {
		return "redirect:/blogs";
	}
}