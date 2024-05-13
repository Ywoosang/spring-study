package ywoosang.springjpa.shop.repository;

import lombok.Getter;
import lombok.Setter;
import ywoosang.springjpa.shop.domain.OrderStatus;

@Getter @Setter
public class OrderSearch  {
    private String memberName; // 사용자명
    private OrderStatus orderStatus; // 주문 상태
}
