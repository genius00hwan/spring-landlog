package com.landvibe.landlog.controller;

import com.landvibe.landlog.controller.form.LoginForm;
import com.landvibe.landlog.controller.form.MemberForm;
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
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping(value = "/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}

	@PostMapping(value = "/members/new")
	public String create(MemberForm form) {

		Member member = Member.builder()
			.name(form.getName())
			.email(form.getEmail())
			.password(form.getPassword())
			.build();

		memberService.join(member);
		return "redirect:/";
	}

	@GetMapping(value = "/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}

	@GetMapping(value = "/members/login")
	public String createLogInForm() {
		return "/members/loginForm";
	}

	@PostMapping(value = "/members/login")
	public String logIn(LoginForm logInForm, RedirectAttributes redirectAttributes) {
		Long memberId;
		memberId = memberService.logIn(logInForm.getEmail(), logInForm.getPassword());
		redirectAttributes.addAttribute("creatorId", memberId);
		return "redirect:/blogs";
	}
}