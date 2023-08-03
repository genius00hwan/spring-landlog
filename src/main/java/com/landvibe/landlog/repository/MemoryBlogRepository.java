package com.landvibe.landlog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.landvibe.landlog.domain.Blog;

@Repository
public class MemoryBlogRepository implements BlogRepository {
	private static Map<Long, Blog> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Long save(Blog blog) {
		blog.setId(++sequence);
		store.put(blog.getId(), blog);
		return blog.getId();
	}

	@Override
	public Long update(Long blogId, Blog updateBlog) {
		updateBlog.setId(blogId);
		store.put(blogId, updateBlog);
		return blogId;
	}

	@Override
	public Long delete(Long blogId) {
		store.remove(blogId);
		return blogId;
	}

	@Override
	public List<Blog> findBlogListByCreatorId(Long creatorId) {
		return store.values().stream()
			.filter(blog -> blog.getCreatorId().equals(creatorId))
			.toList();
	}

	@Override
	public Optional<Blog> findByBlogId(Long blogId) {
		return Optional.ofNullable(store.get(blogId));
	}

	public void clearStore() {
		store.clear();
	}
}
