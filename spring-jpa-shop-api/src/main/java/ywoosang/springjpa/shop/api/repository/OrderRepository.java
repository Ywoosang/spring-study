package ywoosang.springjpa.shop.api.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ywoosang.springjpa.shop.api.domain.Order;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 주문 검색
//        동적 쿼리
//        아래 코드에서 만약 OrderSearch 에서 memberName, orderStatus 가 있고 없고에 따라 동적으로 쿼리를 진행해야 하는 경우
//        return em.createQuery("select o from Order o join o.member m " +
//                        "where o.status = :status " +
//                        "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                // 최대 1000개 까지 제한한다.
//                .setMaxResults(1000)
//                .getResultList();

    public List<Order> findAll(OrderSearch orderSearch) {
            String jpql = "select o From Order o join o.member m";
            boolean isFirstCondition = true;
            //주문 상태 검색
            if (orderSearch.getOrderStatus() != null) {
                jpql += " where";
                isFirstCondition = false;
                jpql += " o.status = :status";
            }
            //회원 이름 검색
            if (StringUtils.hasText(orderSearch.getMemberName())) {
                if (isFirstCondition) {
                    jpql += " where";
                } else {
                    jpql += " and";
                }
                jpql += " m.name like :name";
            }

            // 파라미터 설정
            TypedQuery<Order> query = em.createQuery(jpql, Order.class) .setMaxResults(1000); //최대 1000건
            if (orderSearch.getOrderStatus() != null) {
                query = query.setParameter("status", orderSearch.getOrderStatus());
            }
            if (StringUtils.hasText(orderSearch.getMemberName())) {
                query = query.setParameter("name", orderSearch.getMemberName());
            }
            return query.getResultList();
    }
    // 동적 쿼리를 해결하기 위해 jpql 을 자바 코드로 작성할 수 있도록 jpa 가 표준으로 제공한다. (JPA Criteria)
    // 하지만 코드가 가독성이 없고 유지보수하기 어려워 사용하지 않고 대신 QueryDSL 을 이용해 동적 쿼리 문제를 해결한다.

}
