package ywoosang.springjpa.shop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ywoosang.springjpa.shop.domain.item.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            // persist 는 호출된 객체의 식별자(여기서는 id)가 null 인 영속화되지 않은 새 객체에 사용된다.
            // persist 메소드는 새로운 엔티티를 영속성 컨텍스트에 추가한다.
            // 엔티티가 아직 데이터베이스에 저장되지 않은 새로운 객체일 때 사용한다.
            // JPA 가 엔티티를 데이터베이스에 저장하기 위해 SQL INSERT 명령을 생성하며 트랜잭션이 커밋되는 시점에 데이터베이스에 반영된다.
            em.persist(item);
        } else {
            // merge 메소드는 이미 데이터베이스에 저장되어 있는 엔티티의 변경된 정보를 업데이트할 때 사용된다.
            // 공된 엔티티의 식별자를 기반으로 이미 영속성 컨텍스트에 있는 엔티티를 찾아 그 상태를 업데이트한다.
            // 만약 영속성 컨텍스트 내에 해당 엔티티가 없으면 데이터베이스에서 엔티티를 불러와서 업데이트하고 결과 엔티티를 반환한다.
            // merge 는 주어진 엔티티의 id가 null 이 아닌 데이터베이스에 식별자가 있는 엔티티를 대상으로 사용된다.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
