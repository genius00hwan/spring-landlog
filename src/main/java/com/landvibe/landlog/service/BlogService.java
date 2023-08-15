package com.landvibe.landlog.service;

import static com.landvibe.landlog.exception.ErrorMessages.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.exception.BlogException;
import com.landvibe.landlog.repository.BlogRepository;

@Service
public class BlogService {
	private final BlogRepository blogRepository;
	private final MemberService memberService;

	public BlogService(BlogRepository blogRepository, MemberService memberService) {
		this.blogRepository = blogRepository;
		this.memberService = memberService;
	}

	public Blog create(Long creatorId, Blog blog) {
		validateCreator(creatorId);
		validateBlog(blog.getTitle(), blog.getContents());
		return blogRepository.save(blog);
	}

	public Blog update(Long creatorId, Long blogId, Blog updateBlog) {
		validateCreator(creatorId);
		validateBlog(updateBlog.getTitle(), updateBlog.getContents());
		validateBlogId(blogId, updateBlog);

		return blogRepository.update(blogId, updateBlog);
	}

	public Long delete(Long creatorId, Long blogId) {
		Blog blog = findByBlogId(blogId);
		validateCreator(creatorId);
		return blogRepository.delete(blog.getId());
	}

	public List<Blog> findBlogList(Long creatorId) {
		validateCreator(creatorId);
		return blogRepository.findBlogListByCreatorId(creatorId);
	}

	public Blog findByBlogId(Long blogId) {
		Blog blog = blogRepository.findByBlogId(blogId)
			.orElseThrow(() -> new BlogException(NO_BLOG_MESSAGE));
		validateBlogId(blogId, blog);
		return blog;
	}

	public void validateCreator(Long creatorId) {
		memberService.findById(creatorId);
	}

	public void validateBlog(String title, String contents) {
		if (title.equals("")) {
			throw new BlogException(NO_TITLE_MESSAGE);
		}
		if (contents.equals("")) {
			throw new BlogException(NO_CONTENTS_MESSAGE);
		}
	}

	public void validateBlogId(Long blogId, Blog blog) {
		if (!blog.getId().equals(blogId)) {
			throw new BlogException(INVALID_BLOG_ID_MESSAGE);
		}
	}

}
