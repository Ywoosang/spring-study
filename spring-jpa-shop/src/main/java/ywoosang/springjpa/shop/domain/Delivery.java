package ywoosang.springjpa.shop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;


    // 1대 1 관계에서는 foreign_key 를 Order 에 두건 Delivery 에 두건 상관은 없다.
    // 장단점이 있는데 접근이 더 빈번하게 일어나는 테이블에 두는 것이 좋다.
    // 여기서는 Order 에 접근이 더 빈번하게 일어나므로 - 주문을 통해 배송을 찾음
    // Order 에 foreign_key 를 둔다.
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;


    // enum 은 반드시 @Enumerated 애노테이션을 넣어줘야 한다.
    // EnumType 에는 ORDINAL 과 STRING 이 있다.
    // 기본이 ORDINAL 인데 0,1,2 로 숫자가 들어간다.
    // 예를들어 READY 면 0 COMP 면 1 처럼 들어간다.
    // 문제는 READY, COMP 사이에 다른 상태가 (ex STOP) 생기면 COMP 가 밀리게 되는데
    // 기존 COMP 를 조회했던게 다 STOP 을 조회하는것으로 바뀌게 된다.
    // 따라서 STRING 으로 써야 한다.
    @Enumerated(EnumType.STRING)
    // READY (배송 준비), COMP (배송)
    private DeliveryStatus status;

}
