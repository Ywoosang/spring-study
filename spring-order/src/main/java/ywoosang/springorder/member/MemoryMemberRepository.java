package ywoosang.springorder.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class MemoryMemberRepository implements  MemberRepository {

    // 동시성 이슈로 인해 Concurrent HashMap 을 써야 한다.
    // 예제이기 때문에 HashMap 사용
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
