package com.landvibe.landlog.service;

import static com.landvibe.landlog.exception.ErrorMessages.*;
import static com.landvibe.landlog.config.Pattern.*;

import com.landvibe.landlog.domain.Member;
import com.landvibe.landlog.exception.MemberException;
import com.landvibe.landlog.repository.MemberRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

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
		validateNoInput(member.getName(), member.getPassword(), member.getEmail());
		validateInvalidEmail(member.getEmail());
	}

	private void validateNoInput(String name, String password, String email) {
		if (name.equals("")) {
			throw new MemberException(NO_NAME_MESSAGE);
		}
		if (password.equals("")) {
			throw new MemberException(NO_PASSWORD_MESSAGE);
		}
		if (email.equals("")) {
			throw new MemberException(NO_EMAIL_MESSAGE);
		}

	}

	private void validateInvalidEmail(String email) {
		Optional.ofNullable(email)
			.ifPresent(
				e -> {
					Matcher matcher = EMAIL_PATTERN.matcher(e);
					if (!matcher.matches()) {
						throw new MemberException(INVALID_EMAIL_MESSAGE);
						// 이메일 형식이 아닐 때
					}
				}
			);

		memberRepository.findByEmail(email)
			.ifPresent(m -> {
				throw new MemberException(DUPLICATE_EMAIL_MESSAGE);
			});

	}

	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findById(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(NO_MEMBER_MESSAGE));
		return member;
	}

	public Long logIn(String email, String password) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new MemberException(NO_MEMBER_MESSAGE));
		if (!member.getPassword().equals(password)) {
			throw new MemberException(INVALID_PASSWORD_MESSAGE);
		}
		return member.getId();
	}
}