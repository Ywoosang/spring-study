package ywoosang.springadmin.repository;

import org.springframework.stereotype.Repository;
import ywoosang.springadmin.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
// import java.util.* 로 한번에 import 할 수 있다.

// @Repository
public class MemoryMemberRepository implements MemberRepository {
    // 실무에서는 공유되는 변수일 경우 동시성 문제가 있어 concurrent hashmap 을 써야 한다.
    private static Map<Long, Member> store = new HashMap<>();
    // 실무에서는 Atomic Long 등 동시성 문제를 고려해서 작성해야 한다.
    private static long sequence = 0L; // 0, 1, 2 키 값을 생성

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // Optional 로 감싸서 ofNullable 을 쓰면 이 값이 null 이어도 감싸서 반환을 해줄 수 있다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 자바 8의 lambda 사용
        // findAny 를 붙이면 이 값이 Optional 로 반환된다.
        // 루프를 돌리면서 하나를 찾으면 바로 반환되고, 끝까지 돌렸는데 없으면 null 이 포함되서 반환된다.
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        // store 는 Map 이지만 루프를 돌리기 편하게 실뭉서 List 를 많이 사용
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
