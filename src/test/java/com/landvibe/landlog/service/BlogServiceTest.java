package com.landvibe.landlog.service;

import com.landvibe.landlog.domain.Blog;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryBlogRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.landvibe.landlog.exception.ErrorMessages.*;
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
	Blog blog = Blog.builder()
		.creatorId(creatorId)
		.id(blogId)
		.title(title)
		.contents(contents)
		.build();

	Blog updateBlog = Blog.builder()
		.creatorId(creatorId)
		.id(blogId)
		.title(updatedTitle)
		.contents(updatedContents)
		.build();
	Member member = Member.builder()
		.id(creatorId)
		.name("이름")
		.email("email@landvibe.com")
		.password("password")
		.build();

	@BeforeEach
	public void beforeEach() {
		blogService = new BlogService(blogRepository, memberService);
	}

	@AfterEach
	public void afterEach() {
		blogRepository.clearStore();
	}

	@DisplayName("블로그 생성 성공")
	@Test
	public void create() {
		when(blogRepository.save(blog)).thenReturn(blog);
		assertEquals(blog, blogService.create(blogId, blog));
		verify(blogRepository, times(1)).save(blog);
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 제목")
	@Test
	public void create_invalidTitle() {
		Blog invalidBlog = Blog.builder()
			.creatorId(creatorId)
			.title(invalidTitle)
			.contents(contents)
			.build();

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(creatorId, invalidBlog));
		assertEquals(NO_TITLE_MESSAGE.getErrorMessage(), e.getMessage());
	}

	@DisplayName("블로그 생성 실패 -> 잘못된 내용")
	@Test
	public void create_invalidContents() {
		Blog invalidBlog = Blog.builder()
			.creatorId(creatorId)
			.title(title)
			.contents(invalidContents)
			.build();

		Exception e = assertThrows(Exception.class,
			() -> blogService.create(invalidBlog.getCreatorId(), invalidBlog));
		assertEquals(NO_CONTENTS_MESSAGE.getErrorMessage(), e.getMessage());
	}

	@DisplayName("블로그 업데이트 성공")
	@Test
	public void update() {
		when(blogRepository.update(blogId, updateBlog)).thenReturn(blog);
		when(blogRepository.findByBlogId(blogId)).thenReturn(Optional.ofNullable(updateBlog));

		Long updateBlogId = blogService.update(creatorId, blogId, updateBlog).getId();
		Blog targetBlog = blogService.findByBlogId(blogId);

		assertEquals(blogId, updateBlogId);
		assertEquals(updatedTitle, targetBlog.getTitle());
		assertEquals(updatedContents, targetBlog.getContents());
		verify(blogRepository, times(1)).update(blogId, updateBlog);
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 제목")
	@Test
	public void update_invalidTitle() {
		Blog invalidUpdateBlog = Blog.builder()
			.creatorId(creatorId)
			.id(blogId)
			.title(invalidTitle)
			.contents(updatedContents)
			.build();

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(creatorId, blogId, invalidUpdateBlog));
		assertEquals(e.getMessage(), NO_TITLE_MESSAGE.getErrorMessage());
	}

	@DisplayName("블로그 업데이트 실패 -> 잘못된 내용")
	@Test
	public void update_invalidContents() {
		Blog invalidUpdateBlog = Blog.builder()
			.creatorId(creatorId)
			.id(blogId)
			.title(updatedTitle)
			.contents(invalidContents)
			.build();

		Exception e = assertThrows(Exception.class,
			() -> blogService.update(creatorId, blogId, invalidUpdateBlog));
		assertEquals(e.getMessage(), NO_CONTENTS_MESSAGE.getErrorMessage());
	}

	@DisplayName("블로그 삭제 성공")
	@Test
	public void delete() {
		when(blogRepository.delete(blogId)).thenReturn(blogId);
		when(blogRepository.findByBlogId(blogId)).thenReturn(Optional.ofNullable(blog));

		blogService.delete(creatorId, blogId);

		verify(blogRepository, times(1)).delete(blogId);
	}

	@DisplayName("블로그 삭제 실패")
	@Test
	public void delete_fail() {
		blogService.create(creatorId, blog);

		Exception e = assertThrows(Exception.class,
			() -> blogService.delete(creatorId, 0L));
		assertEquals(e.getMessage(), NO_BLOG_MESSAGE.getErrorMessage());

		verify(blogRepository, times(1)).findByBlogId(0L);
		verify(blogRepository, never()).delete(any(Long.class));
	}
}