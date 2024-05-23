package ywoosang.springjpa.shop.api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.api.domain.Member;
import ywoosang.springjpa.shop.api.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    //@Autowired
    //EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //
        Member member = new Member();
        member.setName("woosang");

        Long savedId = memberService.join(member);

        // DB 에 반영
        // em.flush();

        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {

        Member member1 = new Member();
        member1.setName("woosang");

        Member member2 = new Member();
        member2.setName("woosang");

        memberService.join(member1);
        // memberService.join(member2); // 예외가 발생한다.

        // assertThrows(예외 유형, 예외 유발할 수 있는 코드 블록, 예외 발생시 표시될 메시지);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        }, "예외가 발생해야 한다.");
    }

}