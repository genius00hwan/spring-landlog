package com.landvibe.landlog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;
import com.landvibe.landlog.service.MemberService;

@RestController
@RequestMapping("v1/api/blogs")
public class BlogApiController {

	private final BlogService blogService;
	private final MemberService memberService;

	public BlogApiController( BlogService blogService, MemberService memberService) {
		this.blogService = blogService;
		this.memberService = memberService;
	}

	@GetMapping
	public List<Blog> list(@RequestParam Long creatorId) {
		return blogService.findBlogList(creatorId);
	}

	@PostMapping
	public Blog create(@RequestParam Long creatorId, @RequestBody Blog blog) {
		memberService.findById(creatorId);
		return blogService.create(blog);
	}

	@GetMapping(value = "/{blogId}")
	public Blog get(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId) {
		return blogService.findByBlogId(blogId);
	}

	@PutMapping(value = "/{blogId}")
	public Blog update(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId, @RequestBody Blog blog) {
		return blogService.update(blog);
	}

	@DeleteMapping(value = "/{blogId}")
	public void delete(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId) {
		blogService.delete(blogId);
	}
}
