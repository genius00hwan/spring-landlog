package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.BlogForm;
import com.landvibe.landlog.controller.form.BlogUpdateForm;
import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;
import com.landvibe.landlog.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.landvibe.landlog.constants.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

	@Mock
	private MemoryBlogRepository blogRepository;

	@Mock
	private MemberService memberService;

	BlogService blogService;

	String title = "제목";
	String contents = "내용";
	String updatedTitle = "새 제목";
	String updatedContents = "새 내용";
	String invalidTitle = "";
	String invalidContents = "";
	Long creatorId = 1L;
	Long blogId = 1L;

	BlogForm blogForm;
	BlogUpdateForm updateForm;
	Blog blog;
	Blog updateBlog;
	Member member;

	@BeforeEach
	public void beforeEach() {
		blogService = new BlogService(blogRepository, memberService);
		blogForm = new BlogForm(title, contents);
		updateForm = new BlogUpdateForm(creatorId, blogId, updatedTitle, updatedContents);
		blog = new Blog(creatorId, blogForm.getTitle(), blogForm.getContents());
		updateBlog = new Blog(updateForm.getCreatorId(), updateForm.getTitle(), updateForm.getContents());
		blog.setId(blogId);
		updateBlog.setId(blogId);
		member = new Member("name", "email@landvibe.com", "password");
	}

	@AfterEach
	public void afterEach() {
		blogRepository.clearStore();
	}

	@DisplayName("블로그 생성 성공")
	@Test
	public void create() {
		when(blogRepository.save(blog)).thenReturn(blogId);
		assertEquals(blogId, blogService.create(blog));
		verify(blogRepository, times(1)).save(blog);
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 제목")
	@Test
	public void create_invalidTitle() {
		Blog invalidBlog = new Blog(creatorId, invalidTitle, contents);
		Exception e = assertThrows(Exception.class,
			() -> blogService.create(invalidBlog));
		assertEquals(e.getMessage(), NO_TITLE.get());
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 내용")
	@Test
	public void create_invalidContents() {
		Blog invalidBlog = new Blog(creatorId, title, invalidContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(invalidBlog));
		assertEquals(e.getMessage(), NO_CONTENTS.get());
	}

	@DisplayName("블로그 업데이트 성공")
	@Test
	public void update() {
		when(blogRepository.update(blogId, updateBlog)).thenReturn(blogId);
		when(blogRepository.findByBlogId(blogId)).thenReturn(Optional.ofNullable(updateBlog));

		Long updateBlogId = blogService.update(updateBlog);
		Blog targetBlog = blogService.findByBlogId(blogId);

		assertEquals(blogId, updateBlogId);
		assertEquals(updatedTitle, targetBlog.getTitle());
		assertEquals(updatedContents, targetBlog.getContents());
		verify(blogRepository, times(1)).update(blogId,updateBlog);
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 제목")
	@Test
	public void update_invalidTitle() {
		Blog invalidUpdateBlog = new Blog(creatorId, invalidTitle, updatedContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(invalidUpdateBlog));
		assertEquals(e.getMessage(), NO_TITLE.get());
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 내용")
	@Test
	public void update_invalidContents() {
		Blog invalidUpdateBlog = new Blog(creatorId, updatedTitle, invalidContents);

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(invalidUpdateBlog));
		assertEquals(e.getMessage(), NO_CONTENTS.get());
	}

	@DisplayName("블로그 삭제 성공")
	@Test
	public void delete() {
		when(blogRepository.delete(blogId)).thenReturn(blogId);
		when(blogRepository.findByBlogId(blogId)).thenReturn(Optional.ofNullable(blog));

		blogService.delete(blogId);

		verify(blogRepository, times(1)).delete(blogId);
	}

	@DisplayName("블로그 삭제 실패")
	@Test
	public void delete_fail() {
		blogService.create(blog);

		Exception e = assertThrows(Exception.class,
			() -> blogService.delete(0L));
		assertEquals(e.getMessage(), NO_BLOG.get());

		verify(blogRepository, times(1)).findByBlogId(0L);
		verify(blogRepository, never()).delete(any(Long.class));
	}
}