package ywoosang.springadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ywoosang.springadmin.domain.Member;

import java.util.Optional;

// JpaRepository 는 인터페이스이므로 인터페이스가 인터페이스를 받을 때는 extends 를 쓴다.
// 다중 상속
// 인터페이스를 만들고 SpringDataJPA 가 제공하는 JpaRepository 만 상속받으면 스프링 데이터 JPA 가
// 스프링 데이터 JPA 가 인터페이스에 대한 구현체를 만들고 빈에 등록한다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
