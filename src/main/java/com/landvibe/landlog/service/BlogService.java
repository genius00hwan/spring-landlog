package com.landvibe.landlog.service;

import static com.landvibe.landlog.constants.ErrorMessages.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.landvibe.landlog.domain.Blog;
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
		validateCreatorOfBlog(creatorId,blog);
		validateBlog(blog.getTitle(), blog.getContents());
		return blogRepository.save(blog);
	}

	public Blog update(Long creatorId, Long blogId, Blog updateBlog) {
		validateCreatorOfBlog(creatorId,updateBlog);
		validateBlog(updateBlog.getTitle(), updateBlog.getContents());
		validateBlogId(blogId,updateBlog);

		return blogRepository.update(blogId, updateBlog);
	}

	public Long delete(Long creatorId, Long blogId) {
		Blog blog = findByBlogId(blogId);
		validateCreatorOfBlog(creatorId,blog);
		return blogRepository.delete(blog.getId());
	}

	public List<Blog> findBlogList(Long creatorId) {
		validateCreator(creatorId);
		return blogRepository.findBlogListByCreatorId(creatorId);
	}

	public Blog findByBlogId(Long blogId) {
		Blog blog =  blogRepository.findByBlogId(blogId)
			.orElseThrow(() -> new IllegalArgumentException(NO_BLOG.get()));
		validateBlogId(blogId,blog);
		return blog;
	}
	public void validateCreatorOfBlog(Long creatorId, Blog blog){
		validateCreator(creatorId);
		if (!blog.getCreatorId().equals(creatorId)){
			throw new IllegalArgumentException(INVALID_CREATOR_ID.get());
		}
	}

	public void validateCreator(Long creatorId) {
		memberService.findById(creatorId);
	}

	public void validateBlog(String title, String contents) {
		if (title.equals("")) {
			throw new IllegalArgumentException(NO_TITLE.get());
		}
		if (contents.equals("")) {
			throw new IllegalArgumentException(NO_CONTENTS.get());
		}
	}
	public void validateBlogId(Long blogId, Blog blog){
		if (!blog.getId().equals(blogId)){
			throw new IllegalArgumentException(INVALID_BLOG_ID.get());
		}
	}

}
