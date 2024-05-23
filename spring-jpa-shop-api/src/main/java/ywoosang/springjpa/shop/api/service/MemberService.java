package ywoosang.springjpa.shop.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.api.domain.Member;
import ywoosang.springjpa.shop.api.repository.MemberRepository;

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


    // 트랜잭션이 시작되고 JPA 가 영속성 컨텍스트에서 member 를 가져온다.
    // member 는 영속 상태가 된다.
    // 영속상태의 member 를 setName 으로 이름을 바꿔주면 @Transactional 애노테이션에 의해
    // 관련된 AOP 가 끝나는 시점에 트랜젝션이 커밋된다.
    // 이때 JPA 가 flush 하고 영속성 컨텍스트를 데이터베이스에 commit 한다.
    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
        // 변경 감지에 의해 수정
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
