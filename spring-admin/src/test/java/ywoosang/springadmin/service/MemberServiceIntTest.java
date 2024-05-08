package ywoosang.springadmin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springadmin.domain.Member;
import ywoosang.springadmin.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
public class MemberServiceIntTest {
    // 테스트는 필드 기반으로 Autowired 받는 것이 편하다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;



    // 보통 테스트용 DB 를 따로 구축한다.
    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");
        Long saveId = memberService.join(member);    // when

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void duplicate_member_check() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // 예외가 터져야할 케이스 가정
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // then
    }
}
