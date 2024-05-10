package ywoosang.springjpa.shop.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // ArrayList 같은 컬렉션은 필드에서 초기화한다.
    // 하이버네이트는 엔티티를 영속화할 때 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다.
    // 만약 getOrders() 처럼 임의의 메서드에서 컬렉션을 잘못 생성하면 하이버네이트 내부 매커니즘에 문제가 발생할 수 있다.
    // 따라서 필드 레벨에서 생성하는 것이 가장 안전하고 코드도 간결하다.
    // order 테이블에 있는 member 필드에 의해 읽기 전용이 된다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
