package com.landvibe.landlog.service;

import static com.landvibe.landlog.constants.ErrorMessages.*;
import static com.landvibe.landlog.constants.Pattern.*;

import com.landvibe.landlog.controller.form.LoginForm;
import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.repository.MemberRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {


	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long join(Member member) {
		validateInvalidMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	void validateInvalidMember(Member member) {
		validateNoInput(member.getName(),member.getPassword());
		validateInvalidEmail(member.getEmail());
	}

	private void validateNoInput(String name, String password) {
		if (name.equals("")) {
			throw new IllegalArgumentException(NO_NAME.get());
		}
		if (password.equals("")) {
			throw new IllegalArgumentException(NO_PASSWORD.get());
		}
	}

	private void validateInvalidEmail(String email) {
		Optional.ofNullable(email)
			.ifPresentOrElse(
				e -> {
					Matcher matcher = EMAIL_PATTERN.matcher(e);
					if (!matcher.matches()) {
						throw new IllegalArgumentException(INVALID_EMAIL.get());
					}
				},
				() -> {
					throw new IllegalArgumentException(INVALID_EMAIL.get());
				}
			);

		memberRepository.findByEmail(email)
			.ifPresent(m -> {
				throw new IllegalStateException(DUPLICATE_EMAIL.get());
			});

	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findById(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException(NO_MEMBER.get()));
		return member;
	}

	public Long logIn(LoginForm logInForm) {
		Member member = memberRepository.findByEmail(logInForm.getEmail())
			.orElseThrow(() -> new IllegalArgumentException(NO_MEMBER.get()));
		if (!member.getPassword().equals(logInForm.getPassword())) {
			throw new IllegalArgumentException(INVALID_PASSWORD.get());
		}
		return member.getId();
	}
}