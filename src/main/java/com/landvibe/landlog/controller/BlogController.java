package com.landvibe.landlog.controller;

import java.util.List;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("blogs")
public class BlogController {

	private final MemberService memberService;

	public BlogController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping
	public String blog(@RequestParam(name = "creatorId") Long creatorId, Model model) {
		String name = memberService.findById(creatorId).getName();

		model.addAttribute("creatorId", creatorId);
		model.addAttribute("name", name);

		return "blogs/blogList";
	}

	@GetMapping("/new")
	public String createBlogForm(@RequestParam(name = "creatorId") Long creatorId, Model model) {
		String name = memberService.findById(creatorId).getName();

		model.addAttribute("creatorId", creatorId);
		model.addAttribute("name", name);

		return "blogs/createBlogForm";
	}

	@PostMapping("/new")
	public String createBlog(@RequestParam(name = "creatorId") Long creatorId, BlogForm blogForm,
		RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("creatorId", creatorId);

		return "redirect:/blogs";
	}

	@GetMapping("/update")
	public String updateBlogForm(@RequestParam(name = "creatorId") Long creatorId,
		@RequestParam(name = "blogId") Long blogId, Model model) {
		String name = memberService.findById(creatorId).getName();
		model.addAttribute("creatorId", creatorId);
		model.addAttribute("blogId", blogId);
		model.addAttribute("name", name);

		return "blogs/updateBlogForm";
	}

	@PostMapping("/update")
	public String updateBlog(BlogUpdateForm updateForm, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("creatorId", updateForm.getCreatorId());
		redirectAttributes.addAttribute("blogId", updateForm.getId());

		return "redirect:/blogs";
	}

	@PostMapping("/delete")
	public String deleteBlog(@RequestParam(name = "creatorId") Long creatorId,
		@RequestParam(name = "blogId") Long blogId, RedirectAttributes redirectAttributes) {
		redirectAttributes.addAttribute("creatorId", creatorId);
		redirectAttributes.addAttribute("blogId", blogId);

		return "redirect:/blogs";
	}
}

