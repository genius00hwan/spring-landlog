package com.landvibe.landlog.controller;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BlogController {

	private final MemberService memberService;

	public BlogController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/blogs")
	public String blog(@RequestParam Long creatorId, Model model) {
		Member member = memberService.findById(creatorId);
		model.addAttribute("name", member.getName());
		return "blogs/blogList";
	}

}
