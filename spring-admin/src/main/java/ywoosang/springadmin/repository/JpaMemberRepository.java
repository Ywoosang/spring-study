package ywoosang.springadmin.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import ywoosang.springadmin.domain.Member;

import java.util.List;
import java.util.Optional;


// jpa 에서 데이터를 저장하거나 변경할때는 항상 트랜잭션 안에서 실행되어야 한다.
@Transactional
public class JpaMemberRepository implements MemberRepository {

    // JPA 는 EntityManager 을 사용한다.
    // JPA 라이브러리를 받으면 스프링 부트가 자동으로 설정해놓은 데이터베이스와 연동해 내부적으로 엔티티매니저를 만들어준다.
    private final EntityManager em;

    // 만들어진 것을 inject 받으면 된다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // 자동적으로 id 생성
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {

        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 엔티티를 조횔할 때 select m 은 member 엔티티 자체를 조회한다는 것을 의미한다.
        // 이미 매핑이 다 되어 있기 때문에 가능하다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
