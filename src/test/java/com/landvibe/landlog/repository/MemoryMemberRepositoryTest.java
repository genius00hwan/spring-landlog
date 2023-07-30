package com.landvibe.landlog.repository;

import com.landvibe.landlog.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

	MemoryMemberRepository repository = new MemoryMemberRepository();

	@AfterEach
	public void afterEach() {
		repository.clearStore();
	}

	@Test
	void save() {
		//given
		Member member = new Member("수환", "ksh@landvibe.com", "1234");

		//when
		repository.save(member);

		//then
		Member result = repository.findById(member.getId()).get();
		assertThat(result).isEqualTo(member);
	}

	@Test
	public void findByName() {
		//given
		Member member1 = new Member("수환", "ksh@landvibe.com", "1234");
		repository.save(member1);
		Member member2 = new Member("동하", "dong@landvibe.com", "5678");
		repository.save(member2);

		//when
		Member result = repository.findByName("수환").get();

		//then
		assertThat(result).isEqualTo(member1);
	}

	@Test
	public void findAll() {
		//given
		Member member1 = new Member("승철", "tmdcheol@landvibe.com", "1234");
		repository.save(member1);
		Member member2 = new Member("준영", "jyp@landvibe.com", "5678");
		repository.save(member2);

		//when
		List<Member> result = repository.findAll();

		//then
		assertThat(result.size()).isEqualTo(2);
	}

	@Test
	void findByEmail() {
		Member member1 = new Member("재승", "jaewin@landvibe.com", "password");
		repository.save(member1);

		Member member2 = new Member("주민", "zoomin@landvibe.com", "password");
		repository.save(member2);

		//when
		Member result = repository.findByEmail(member1.getEmail()).get();

		//then
		assertThat(member1).isEqualTo(result);
	}

	@Test
	void findById() {
		//given
		Member member = new Member("선우", "mentor@landvibe.com", "spring");
		repository.save(member);

		//when
		Member result = repository.findById(member.getId()).get();

		//then
		Assertions.assertThat(result).isEqualTo(member);
	}


}