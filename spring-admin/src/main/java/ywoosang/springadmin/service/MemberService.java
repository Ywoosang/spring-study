package ywoosang.springadmin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ywoosang.springadmin.domain.Member;
import ywoosang.springadmin.repository.MemberRepository;
import ywoosang.springadmin.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

// @Service
public class MemberService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 외부에서 넣어주도록 한다.
    private final MemberRepository memberRepository;

    // MemberRepository 의 구현체로 MemoryMemberRepository 가 있으므로 이것을 스프링 컨테이너가 서비스에 주입해준다.
    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /**
     * 회원 가입
     * @param member
     */
    public Long join (Member member) {
        // 같은 이름이 있는 중복 회원 X
        // Optional 로 감싸면 이 안에 멤버 객체가 있기 때문에 옵셔널을 통한 여러 메소드들을 사용할 수 있다.
        // orElseGet : 값이 있으면 꺼내고 없으면 꺼내지 않음
        // 중복 회원 검증
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        // 이부분에서 findByName 이 정상동작 하지 않는 것 같다.
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 점체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
