package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.landvibe.landlog.constants.ErrorMessages.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

	MemberService memberService;
	MemoryMemberRepository memberRepository;
	Member member;
	String name = "수환";
	String email = "ksh@landvibe.com";
	String password = "1234";
	String notEmail = "invalidEmail";
	String invalidPassword = "45556";
	String invalidEmail = "invalid@landvibe.com";
	String empty = "";

	@BeforeEach
	public void beforeEach() {
		memberRepository = new MemoryMemberRepository();
		memberService = new MemberService(memberRepository);
		member = new Member(name, email, password);
	}

	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}

	@Test
	public void 회원가입_성공() {
		//Given

		//When
		Long saveId = memberService.join(member);
		//Then
		Member findMember = memberRepository.findById(saveId).get();
		assertEquals(member, findMember);
	}

	@Test
	public void 이메일_형식_예외() {
		Member member2 = new Member(name, notEmail, password);
		//when
		Exception e = assertThrows(Exception.class,
			() -> memberService.join(member2));
		assertThat(e.getMessage()).isEqualTo(INVALID_EMAIL.get());
	}

	@Test
	public void 이름_입력안함() {
		Member member = new Member(empty, email, password);
		//when
		Exception e = assertThrows(Exception.class,
			() -> memberService.join(member));
		assertThat(e.getMessage()).isEqualTo(NO_NAME.get());
	}

	@Test
	public void 비밀번호_입력안함() {
		Member member = new Member(name, email, empty);
		//when
		Exception e = assertThrows(Exception.class,
			() -> memberService.join(member));
		assertThat(e.getMessage()).isEqualTo(NO_PASSWORD.get());
	}

	@Test
	public void 중복_이메일_예외() throws Exception {
		//given
		Member member2 = new Member("동하", email, "4567");
		//when
		memberService.join(member);
		IllegalStateException e = assertThrows(IllegalStateException.class,
			() -> memberService.join(member2));//예외가 발생해야 한다.
		assertThat(e.getMessage()).isEqualTo(DUPLICATE_EMAIL.get());
	}

	@Test
	public void 로그인_성공() {
		//given
		//when
		memberService.join(member);
		LoginForm loginForm = new LoginForm(email, password);

		//then
		Long memberId = memberService.logIn(loginForm);
		assertThat(memberId).isEqualTo(member.getId());
	}

	@Test
	public void 로그인_실패_잘못된이메일() {
		//given
		//when
		memberService.join(member);
		LoginForm loginForm = new LoginForm(invalidEmail, password);

		//then
		Exception e = assertThrows(Exception.class,
			() -> memberService.logIn(loginForm));
		assertThat(e.getMessage()).isEqualTo(NO_MEMBER.get());
	}

	@Test
	public void 로그인_실패_잘못된비밀번호() {
		//when
		memberService.join(member);
		LoginForm loginForm = new LoginForm(email, invalidPassword);

		//then
		Exception e = assertThrows(Exception.class,
			() -> memberService.logIn(loginForm));
		assertThat(e.getMessage()).isEqualTo(INVALID_PASSWORD.get());
	}
}