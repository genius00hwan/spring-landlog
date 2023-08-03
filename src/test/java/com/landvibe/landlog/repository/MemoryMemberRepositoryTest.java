package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();
	Member member;
	Member member2;
	String name = "수환";
	String email = "ksh@landvibe.com";
	String password = "1234";

	@BeforeEach
	public void beforEach(){
		member = new Member(name, email, password);
		member2 = new Member("동하", "dong@landvibe.com", "5678");
	}
	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	void save() {
		//given

		//when
		repository.save(member);

		//then
		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);
	}

	@Test
	public void findByName() {
		//given
		repository.save(member);

		repository.save(member2);

		//when
		Member result = repository.findByName(name).get();

		//then
		assertThat(result).isEqualTo(member);
	}

	@Test
	public void findAll() {
		//given
		repository.save(member);
		repository.save(member2);

		//when
		List<Member> result = repository.findAll();

		//then
		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	void findByEmail() {
		repository.save(member);
		repository.save(member2);

		//when
		Member result = repository.findByEmail(member.getEmail()).get();

		//then
		assertThat(member).isEqualTo(result);
	}

	@Test
	void findById() {
		//given
		repository.save(member);

		//when
		Member result = repository.findById(member.getId()).get();

		//then
		Assertions.assertThat(result).isEqualTo(member);
	}


}