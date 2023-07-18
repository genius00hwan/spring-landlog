package com.landvibe.landlog.service;

import com.landvibe.landlog.controller.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {

	private static final String EMAIL_REGEX =
		"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
			+ "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\""
			+ "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@"
			+ "(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
			+ "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
			+ "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:"
			+ "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long join(Member member) {
		validateInvalidInput(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateInvalidInput(Member member) {
		validateNoInput(member);
		validateInvalidEmail(member.getEmail());
		validateDuplicateMember(member);
	}

	private void validateNoInput(Member member) {
		if (member.getName().equals("")) {
			throw new IllegalArgumentException("이름을 입력해주세요.");
		}
		if (member.getPassword().equals("")) {
			throw new IllegalArgumentException("비밀번호를 입력해주세요.");
		}
	}

	private void validateInvalidEmail(String email) {
		Optional.ofNullable(email)
			.ifPresentOrElse(
				e -> {
					Matcher matcher = EMAIL_PATTERN.matcher(e);
					if (!matcher.matches()) {
						throw new IllegalArgumentException("이메일을 입력해주세요.");
					}
				},
				() -> {
					throw new IllegalArgumentException("이메일을 입력해주세요.");
				}
			);

	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByEmail(member.getEmail())
			.ifPresent(m -> {
				throw new IllegalStateException("중복된 이메일입니다.");
			});
	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findById(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		return member;
	}

	public Long logIn(LoginForm logInForm) {
		Member member = memberRepository.findByEmail(logInForm.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));
		if (!member.getPassword().equals(logInForm.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return member.getId();
	}
}
