package ywoosang.springjpa.shop.api;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.api.domain.*;
import ywoosang.springjpa.shop.api.domain.item.Book;

/**
 * 총 주문 2개
 * * userA
 *   JPA1 BOOK
 *   JPA2 BOOK
 * * userB
 *   SPRING1 BOOK
 *   SPRING2 BOOK
 */

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    // 스프링 빈이 다 올라오고 나면 호출해주는 애노테이션
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    // @PostConstruct 안에서 @Transactional 을 이용할 수 없다.
    // 별도의 빈으로 등록해 주어야 한다.
    @Component
    @Transactional
    @RequiredArgsConstructor
    // static 내부 클래스는 외부 클래스의 인스턴스와 무관하게 독립적으로 빈으로 등록될 수 있으므로
    // 스프링이 이 클래스를 빈으로 관리하는데 문제가 없다.
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA","서울", "1", "1111");
            em.persist(member);

            Book book1 = Book.createBook("JPA1 BOOK", "우상", "1234", 10000, 100);
            em.persist(book1);
            Book book2 = Book.createBook("JPA1 BOOK", "우상", "1234", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = getDelivery(member);
            // 위에서 만든 member 가 주문을 하도록 생성한다.
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Delivery getDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private static Member createMember(String username, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(username);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        public void dbInit2() {
            Member member = createMember("userB", "경기도", "2", "2222");
            em.persist(member);

            Book book1 = Book.createBook("SPRING BOOK", "우상", "1234", 20000, 200);
            em.persist(book1);
            Book book2 = Book.createBook("SPRING BOOK", "우상", "1234", 40000, 400);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = getDelivery(member);
            // 위에서 만든 member 가 주문을 하도록 생성한다.
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }
    }
}

