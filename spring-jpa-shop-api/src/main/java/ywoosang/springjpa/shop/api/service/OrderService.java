package ywoosang.springjpa.shop.api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ywoosang.springjpa.shop.api.domain.Delivery;
import ywoosang.springjpa.shop.api.domain.Member;
import ywoosang.springjpa.shop.api.domain.Order;
import ywoosang.springjpa.shop.api.domain.OrderItem;
import ywoosang.springjpa.shop.api.domain.item.Item;
import ywoosang.springjpa.shop.api.repository.ItemRepository;
import ywoosang.springjpa.shop.api.repository.MemberRepository;
import ywoosang.springjpa.shop.api.repository.OrderSearch;
import ywoosang.springjpa.shop.api.repository.OrderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        // 개발자가 한 명일때는 createOrderItem 을 이용해 생성하는 것만 이용하면 된다.
        // 만약 다른 개발자와 함께 협업한다면
        // OrderItem orderItem = new OrderItem();
        // orderItem.setCount();
        // 처럼 다른 형식으로 생성할 수 있다.
        // 이럴 경우 일관적이지 않은 코드 작성은 유지보수하기 어려워지므로, createOrederItem 이외의 생성을 막아야 한다.
        // 따라서 엔티티의 생성자를 protected 로 변경한다. JPA 에서 생성자를 protected 로 만들면 사용하지 말라는 의미다.
        // 이렇게 코드를 제약하는 스타일로 설계하는게 좋다.
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        // 원래 delivery, orderItem 따로 생성해야 한다.
        // deliveryRepository 에서 save 해서 넣어주고
        // itemRepository.save 해서 넣어줘야 한다.
        // 그런데 orderRepository.save 하나만 호출한 상태다.
        // 이렇게 해도 되는 이유는 orderItems 와 delivery 를 casecade = CascadeType.ALL 로 설정했기 때문이다.
        // Order 를 persist 하면 order 내부의 orderItems 와 delivery 도 persist 가 호출된다.
        // delivery 와 orderItems 는 각 엔티티가 모두 order 만 연관되어 있기 때문에
        // Cascade 를 ALL 로 지정해도 문제가 없다.
        // 만약 연관관계가 복잡하게 얽혀 있는 경우 각각 persist 하는것이 좋다.
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // order 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();

        // 일반적으로 sql 을 직접 다루는 라이브러리 mybatis 나 jdbc 템플릿 등은 데이터를 변경했으면 업데이트 쿼리를 직접 짜서 날려야 한다.
        // 로직은 바꿨으면 그 파라미터를 쿼리에 넣고 다시 sql 문을 날려야한다.
        // 따라서 서비스 계층에서 비즈니스 로직을 다 쓸 수 밖에 없다. JPA 를 활용하면 엔티티 안에서 있는 데이터들만 바꿔주면
        // JPA 가 변경내역 감지(더티체킹) 해서 변경된 내역을 찾아서 데이터베이스에 업데이트 쿼리를 날려준다.
    }


    // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
