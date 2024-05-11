package ywoosang.springjpa.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.domain.Member;
import ywoosang.springjpa.shop.repository.MemberRepository;

import java.util.List;

// JPA 의 데이터 변경은 트랜잭션 안에서 실행되어야 한다.
// @Transactional 을 쓰면 public 메소드들이 트랜잭션에 걸려 들어간다.
// readOnly = true 옵션을 주면 JPA 조회시 성능을 최적화한다.
// 조회가 아닌 삽입, 수정에는 readOnly = true 를 넣으면 안된다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    // @Autowired
    // private MemberRepository memberRepository;

    private final MemberRepository memberRepository;

    // 생성자가 1개일 경우 Autowired 생략 가능
    // @Autowired
    // 롬복을 쓸 경우 final 필드 자동주입
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원가입
    // 떠로 설정한 것은 우선권을 가져 readOnly = true 적용 안된다.
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
