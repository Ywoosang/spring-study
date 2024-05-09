package ywoosang.springjpa.shop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// Junit4 에서 RunWith(SpringRunner.class) 어노테이션은 스프링의 테스트 컨텍스트를 로드해
// 테스트 중에 스프링 빈을 주입받을 수 있도록 해주는 역할을 한다.
// Junit5 에서는 @RunWith 어노테이션이 @ExtendWith 로 대체되었고
// 스프링과 통합하기 위해 @SpringExtension 을 사용한다.

// 엔티티 매니저를 이용한 모든 데이터 변경은 항상 트랜잭션 안에서 일어나야 한다.
@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    // @Rollback(false) 테스트가 끝나도 롤백하고 싶지 않을 경우
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        // findMember == member
        // 같은 트랜잭션 안에서 저장하고 조회하면 영속성 컨텍스트가 같다.
        // 같은 영속성 컨텍스트 안에서는 id 값이 같으면 같은 엔티티로 식별한다.
        assertThat(findMember).isEqualTo(member);


    }
}