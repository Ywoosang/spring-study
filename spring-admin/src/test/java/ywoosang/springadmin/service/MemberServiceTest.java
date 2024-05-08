package ywoosang.springadmin.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ywoosang.springadmin.domain.Member;
import ywoosang.springadmin.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    // MemberService memberService = new MemberService();
    // clear 해주기 위해 가져온다.
    // MemberService 에서 사용하는 리포지토리와 서로 다른 인스턴스다.
    // new 로 생성한 다른 인스턴스임에도 clearStore 로 지울 수 있는건 static 으로 사용한 클래스 멤버 변수기 때문이다.
    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}