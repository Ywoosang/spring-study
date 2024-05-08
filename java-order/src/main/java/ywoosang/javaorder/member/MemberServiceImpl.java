package ywoosang.javaorder.member;

// 구현체가 하나만 있을 때 뒤에 Impl 이라고 관례상 네이밍을 붙인다.
public class MemberServiceImpl implements  MemberService{

    // 구현체가 없이 null 이면 null point exception 발생
    // 구현체를 붙여줘야 한다.
    // new MemoryMemberRepository() 부분에서 구현체를 의존하고 있다.
    // MemberRepository 부분에서 추상화를 의존하고 있다.
    // 따라서 추상화에도 의존하고 구체화에도 의존하고 있다.
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

