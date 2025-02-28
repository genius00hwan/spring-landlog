package com.landvibe.landlog.repository;

import java.util.List;
import java.util.Optional;

import com.landvibe.landlog.domain.Blog;

public interface BlogRepository {
	Blog save(Blog blog);

	Blog update(Long blogId, Blog newBlog);

	Long delete(Long blogId);

	Optional<Blog> findByBlogId(Long blogId);

	List<Blog> findBlogListByCreatorId(Long creatorId);

}
