package ywoosang.springjpa.shop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    // JPA 를 쓰기 때문에 엔티티 매니저가 있어야 한다.
    // 인티티 매니저를 주입해준다.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        // Member 를 반환하지 않고 Id 만 반환하는 이유는
        // 커맨드랑 쿼리를 분리하는 원칙에 의해 저장을 하고 나면 가급적이면 사이드 이펙트를 일으키지 않기 위해서
        // 직접적으로 엔티티를 반환하지 않는다.
        // id 만 있으면 조회할 수 있기 때문에
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

}
