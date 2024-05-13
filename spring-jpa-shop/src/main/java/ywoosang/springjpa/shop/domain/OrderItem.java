package ywoosang.springjpa.shop.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ywoosang.springjpa.shop.domain.item.Item;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

//    createOrderItem 으로만 생성되기를 원하므로
//    @Entity protected 를 설정해서 외부에서 new 로 생성해 조작할 수 없도록 한다.
//    롬복을 사용하면 @NoArgsConstructor(access= AccessLevel.PROTECTED) 로 protected 생성자를 생략할 수 있다.
//    protected OrderItem() {
//    }

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "order_id")
    private Order order;


    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public void cancel() {
        // 주문이 취소되어 원상복구할 경우 주문 수량만큼 다시 늘려준다.
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }

    public static OrderItem  createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        // 재고 감소 반영
        item.removeStock(count);
        return orderItem;
    }
}
