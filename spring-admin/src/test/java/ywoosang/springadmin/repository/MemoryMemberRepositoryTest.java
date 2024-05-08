package ywoosang.springadmin.repository;

//import org.junit.jupiter.api.Assertions;
// import org.assertj.core.api.Assertions; 요즘에는 assertj 를 더 많이 쓰는 추세다.
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ywoosang.springadmin.domain.Member;

// static import 를 사용할 수 있다.
import java.util.List;

import static org.assertj.core.api.Assertions.*;

// class level 에서 테스트를 돌리면 내부 테스트들을 다 돌릴 수 있다.
class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // @Test 어노테이션이 붙은 메서드가 끝날 때마다 동작한다.
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        // Optional 에서 값을 꺼낼 때는 get 을 활용한다.
        // get 으로 바로 꺼내는게 좋은 방법은 아닌데, 테스트코드에서 주로 사용한다.
        Member result = repository.findById(member.getId()).get();
        // System.out.println("result = " + (result == member));
        // 값이 같으면 초록색, 다르면 오류와 함께 빨간색이 뜬다.
        // Assertions.assertEquals(result, member);
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        repository.findByName("spring1");

        // 순서가 보장되지 않기 때문에 이전에 저장되었던 객체가 나와버릴 수 있다.
        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public  void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
