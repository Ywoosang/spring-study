package ywoosang.springjpa.shop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ywoosang.springjpa.shop.domain.Member;

import java.util.List;

// 컴포넌트 스캔
@Repository
@RequiredArgsConstructor
public class MemberRepository {
    // 엔티티 매니저 주입
//    @PersistenceContext
//    private EntityManager em;

    private final EntityManager em;

    // 스프링 부트의 데이터 JPA 를 사용하면 @PersistenceContext
    // 를 @Autowired 로 바꿀 수 있다.
    // 이때 Entity Manager em 이 final 이므로 롬복을 활용해 단순하게 나타낼 수 있다.
//    public MemberRepository(EntityManager em) {
//        this.em = em;
//    }

//엔티티 매니저 팩토리를 직접 주입받을 경우 (참고)
//    @PersistenceUnit
//    private EntityManagerFactory emf;





    public void save(Member member) {
        // 맴버 객체를 넣어 persist 하면 트랜잭션이 commit 되는 순간에 db에 insert 쿼리가 반영된다.
        em.persist(member);
    }

    public Member findOne(Long id) {
        // 단일 조회 (class, pk)
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // createQuery(jpql, 반환타입)
        // sql 은 테이블을 대상으로 쿼리
        // jpql 은 엔티티 객체를 대상으로 쿼리
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
