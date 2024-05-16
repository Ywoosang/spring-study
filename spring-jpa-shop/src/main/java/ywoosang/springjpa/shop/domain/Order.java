package ywoosang.springjpa.shop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;


    // n + 1 problem
    // n: 첫 번째 날린 쿼리가 가져온 값 1: 처음 날린 쿼리
    // @ManyToOne(fetch = FetchTyope.EAGER)
    // JPQL select o from order o;   ->  SQL select * from order
    // JPQL 로 SQL 문으로 번역될 때 100 개를 가져왔다고 가정
    // SQL 날라갈 때는 ORDER 만 가져오는데, 여기서 member 가 EAGER 로 설저되어 있다면
    // member 100 개를 가져오는 쿼리가 추가적으로 발생한다.
    // 따라서 연관관계는 lazy 로 무조건 사용하는게 좋다.
    // @XtoOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩으로 설정해야 한다.

    // 여기에 값을 넣으면 member_id 값이 변경된다.
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    // 양방향 참조관계
    // Order 는 member 를 가지고 있고, Member 는 orders 를 가지고 있다.
    // foreign_key 는 member_id 하나다.
    // 둘의 관계를 바꾸고 싶으면 foreign_key 의 값을 변경해야 한다.
    // Order 의 회원을 바꿀 때 Order 의 member 를 바꿀 수도 있고
    // 반대로 Member 에서 orders 리스트의 값을 바꿀 수도 있다.
    // 둘다 바꿔버리면 JPA 는 둘 중에 뭐가 바뀌었을 때 foreign_key 의 값을 바꿔야할지 모른다.
    // ex) member 에는 값을 세팅을 안했는데 orders 에는 값을 세팅을 안한경우
    // ex) orders 에는 Order 들을 집어넣는데 Order 엔티티에는 member 를 안넣은 경우
    // 이럴 경우 foreign_key 값을 업데이트 하는 건 둘중 하나만 가능하도록 jpa 에서 설계되었다.
    // 객체는 변경할 수 있는 것이 member 와 orders 로 2개인데 테이블은 foreign_key 하나만 변경하면 되기 때문에
    // 둘중 하나를 연관관계 주인으로 잡고 그 값이 변경되었을 때 foreign_key 가 변경되도록 한다.
    // 이 기준은 foreign_key 가 있는 곳으로 잡으면 좋다.
    // 따라서 Order 에 foreign_key 가 위치하므로 Order 의 member 를 기준으로 잡는다.
    // 연관관계 주인이 아닌 Member 의 orders 에는 mappedBy 를 넣는다.

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private Delivery delivery;

    // 예전에는 Date 를 사용하면 날짜 관련 어노테이션 매핑을 해야 했다.
    // 자바8 에스는 LocalDateTime 을 사용하면 Hibernate 가 알아서 자동으로 지원해준다.
    // 주문 시간
    private LocalDateTime orderDate;


    @Enumerated(EnumType.STRING)
    // 주문 상태 [ORDER, CANCEL]
    private OrderStatus status;

    // 엔티티를 설정할 때는 아래처럼한다.
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    // 생성 메서드
    // 주문 생성
    // 가변인수 (Variable Arguments)
    // 메소드가 동일한 타입의 인수를 여러개 받을 수 있도록 해준다.
    // createOrder(member, delivery); 처럼 OrderItems 없이 호출하거나
    // createOrder(member, delivery, orderItem1, orderItem2); 또는
    // createOrder(member, delivery, new OrderItem[]{orderItem1, orderItem2, orderItem3}) 처럼 배열을 직접 전달할 수도 있다.
    // 주문을 생성할때는 이 부분만 바꾸면 된다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    /**
     * 주문 취소
     */
    // 비즈니스 로직
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        // 여러 상품을 주문한 경우 각 상품에 대해서도 cancel 을 해줘야 한다.
        // JPA 가 엔티티에 있는 데이터를 변경하면 업데이트 쿼리를 날려준다.
        // db 에 update 를 하는 부분을 작성할 필요가 없다.
        // JPA 를 사용했을 때 가장 큰 장점이다.
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /**
     * 전체 주문 가격 조회
     */
    // 조회 로직
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
