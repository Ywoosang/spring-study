package ywoosang.springorder.member;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ywoosang.springorder.AppConfig;

public class MemberServiceTest {

    MemberService memberService;

    // 각 @Test 를 실행하기 전 무조건 실행
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }



    @Test
    void join() {
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());

        //
        Assertions.assertEquals(member, findMember);
    }
}
