package ywoosang.springadmin.repository;

import org.springframework.stereotype.Repository;
import ywoosang.springadmin.domain.Member;
import java.util.Optional;
import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    // Optional 은 없으면 null 을 반환하지 않고
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
