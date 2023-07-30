package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //Given
        Member member = new Member("soohwan","ksh@landvibe.com","1234");
        //When
        Long saveId = memberService.join(member);
        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }
    @Test
    public void 이메일_형식_예외(){
        Member member = new Member("yonghyeon","dragonhyeon","1234");
        //when
        Exception e = assertThrows(Exception.class,
            () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo("이메일을 입력해주세요.");
    }
    @Test
    public void 이름_입력안함(){
        Member member = new Member("","spring@landvibe.com","1234");
        //when
        Exception e = assertThrows(Exception.class,
            () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo("이름을 입력해주세요.");
    }
    @Test
    public void 비밀번호_입력안함(){
        Member member = new Member("승철","winFe@landvibe.com","");
        //when
        Exception e = assertThrows(Exception.class,
            () -> memberService.join(member));
        assertThat(e.getMessage()).isEqualTo("비밀번호를 입력해주세요.");
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member("주민","hohoho@landvibe.com","1234");
        Member member2 = new Member("동하","hohoho@landvibe.com","4567");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));//예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("중복된 이메일입니다.");
    }

    @Test
    public void 로그인_성공() {
        //given
        Member member = new Member("준영", "jyp@landvibe.com", "1234");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm( "jyp@landvibe.com", "1234");

        //then
        Long memberId = memberService.logIn(loginForm);
        assertThat(memberId).isEqualTo(member.getId());
    }

    @Test
    public void 로그인_실패_잘못된이메일() {
        //given
        Member member = new Member("재승", "jaewin@landvibe.com", "4567");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm("zoomin@landvibe.com", "4567");

        //then
        Exception e = assertThrows(Exception.class,
            () -> memberService.logIn(loginForm));
        assertThat(e.getMessage()).isEqualTo("잘못된 이메일입니다.");
    }

    @Test
    public void 로그인_실패_잘못된비밀번호(){
        //given
        Member member = new Member("세원", "sewon@landvibe.com", "3434");

        //when
        memberService.join(member);
        LoginForm loginForm = new LoginForm("sewon@landvibe.com", "4545");

        //then
        Exception e = assertThrows(Exception.class,
            () -> memberService.logIn(loginForm));
        assertThat(e.getMessage()).isEqualTo("잘못된 비밀번호입니다.");
    }
}