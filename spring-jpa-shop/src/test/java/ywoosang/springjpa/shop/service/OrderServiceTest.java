package ywoosang.springjpa.shop.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.domain.Address;
import ywoosang.springjpa.shop.domain.Member;
import ywoosang.springjpa.shop.domain.Order;
import ywoosang.springjpa.shop.domain.OrderStatus;
import ywoosang.springjpa.shop.domain.item.Book;
import ywoosang.springjpa.shop.domain.item.Item;
import ywoosang.springjpa.shop.exception.NotEnoughStockException;
import ywoosang.springjpa.shop.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // case
        final int orderCount = 2;
        final int orderPrice = 10000;
        Member member = createMember();

        Item book = createBook("책이름",orderPrice, 10);

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // then

        Order order = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, order.getStatus(),"상품 주문시 상태는 ORDER 여야 한다.");
        // orderItem 한 종류에 2개 count 로 주문했다.
        assertEquals(1,order.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한디.");
        assertEquals(orderPrice*orderCount, order.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }

    private Item createBook(String name, int orderPrice, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity( stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    // expect 로 들어온 Exception 이 터져야 한다.
    // 테스트 메소드 실행 중에 지정된 예외가 발생할 것으로 예상될 때 사용된다.
    // Junit5 에서는 예외 처리를 테스트하는 방법이 변경되어 assertThrows 메서드를 사용해 예외 타입과 발생된 에외의 세부사항까지 검증할 수 있다.
    // @Test(expected= NotEnoughStockException.class) - Junit4 방식

    // 여기서는 통합 테스트를 진행했지만, 주문 상품 재고수량 초과에 대해서는 removeStock 자체에 대해 단위 테스트가 있는게 중요하다.
    // 즉 Item 엔티티의 비즈니스 로직에 대해 테스트하는 단위 테스트가 별도로 존재하는게 필요하다.
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("책이름", 10000, 10);

        int orderCount = 20;
        // when

        // then
        // 람다식 내에서 해당 예외가 발생하면 테스트 케이스 성공
        // 세 번째 메시지는 테스트가 실패했을 때 표시되는 실패 메시지다.
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        }, "재고수량 예외가 발생해야 한다.");

        // 테스트를 실패 처리하는 fail()
        // 이 라인까지 오면 안되는구나 인식할 수 있다.
        // 테스트가 fail() 호출에 도달하면 JUnit 은 AssertionError 를 발생시키고 실패로 처리한다.
        // fail("재고수량 예외가 발생해야 한다.")

    }

    @Test
    public void 주문취소() throws Exception {
        // 주문을 하고 취소를 한 다음 취소가 정상적으로 이루어졌는지 확인하도록 구현
        final int stockQuantity = 10;
        Member member = createMember();
        Item book = createBook("스프링책",  10000, stockQuantity);
        // 주문
        Long orderId = orderService.order(member.getId(), book.getId(), 10);
        // 주문 취소
        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        // 주문 상태를 확인해야 함
        assertEquals(OrderStatus.CANCEL, order.getStatus() ,"주문 취소시 상태는 CANCEL 이어야 한다.");
        assertEquals(stockQuantity, book.getStockQuantity(), "주문 취소를 했을 때 주문 전과 수량이 일치해야 한다.");
    }

}
