package com.landvibe.landlog.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.landvibe.landlog.domain.Blog;

class MemoryBlogRepositoryTest {

	MemoryBlogRepository repository = new MemoryBlogRepository();
	Long creatorId = 1L;
	String title = "제목";
	String contents = "내용";

	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	void save() {
		//given
		Blog blog = new Blog(creatorId, title, contents);

		//when
		repository.save(blog);

		//then
		Blog result = repository.findByBlogId(blog.getId()).get();
		assertThat(result).isEqualTo(blog);
	}

	@Test
	void update() {
		//given
		String expectedTitle = "바뀐 제목";
		String expectedContents = "바뀐 내용";

		Blog blog = new Blog(creatorId, title, contents);
		Blog updatedBlog = new Blog(creatorId, "바뀐 제목", "바뀐 내용");


		//when
		repository.save(blog);
		repository.update(blog.getId(),updatedBlog);

		//then
		Blog result = repository.findByBlogId(updatedBlog.getId()).get();
		assertThat(result.getTitle()).isEqualTo(expectedTitle);
		assertThat(result.getContents()).isEqualTo(expectedContents);
	}

	@Test
	void delete() {
		//given
		Blog blog = new Blog(creatorId, title, contents);
		repository.save(blog);

		//when
		repository.delete(blog.getId());

		//then
		Optional<Blog> result = repository.findByBlogId(blog.getId());
		assertThat(result.isEmpty()).isEqualTo(true);
	}

}