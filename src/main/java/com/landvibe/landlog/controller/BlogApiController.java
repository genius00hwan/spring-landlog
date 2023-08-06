package com.landvibe.landlog.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.service.BlogService;

@RestController
@RequestMapping("v1/api/blogs")
public class BlogApiController {

	private final BlogService blogService;

	public BlogApiController(BlogService blogService) {
		this.blogService = blogService;
	}

	@GetMapping
	public List<Blog> list(@RequestParam Long creatorId) {
		return blogService.findBlogList(creatorId);
	}

	@PostMapping
	public Blog create(@RequestParam Long creatorId, @RequestBody Blog blog) {
		return blogService.create(creatorId, blog);
	}

	@GetMapping(value = "/{blogId}")
	public Blog get(@PathVariable("blogId") Long blogId) {
		return blogService.findByBlogId(blogId);
	}

	@PutMapping(value = "/{blogId}")
	public Blog update(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId, @RequestBody Blog blog) {
		return blogService.update(creatorId, blogId, blog);
	}

	@DeleteMapping(value = "/{blogId}")
	public void delete(@RequestParam Long creatorId, @PathVariable("blogId") Long blogId) {
		blogService.delete(creatorId,blogId);
	}
}
