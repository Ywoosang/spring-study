package ywoosang.springjpa.shop.api.domain.item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ywoosang.springjpa.shop.api.exception.NotEnoughStockException;
import ywoosang.springjpa.shop.api.domain.Category;

import java.util.ArrayList;
import java.util.List;

// 공동속성이므로 추상클래스로 만듬
@Entity
// 상속관계 매핑이기 때문에 strategy 를 저장해야 한다.
// 이 전략을 부모 테이블에 잡아줘야 한다.
// 전략은 SINGLE_TABLE, TABLE_PER_CLASS, JOINED 가 있다.
// SINGLE_TABLE 은 한테이블에 필드로 다 넣는것을 의미한다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Book, Movie, Album 을 구분하기 위해
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 재고를 늘고 주리는 로직
    // 엔티티 자체에서 해결할 수 있는 것은 도메인 주도 설계관점에서 엔티티 안에 비즈니스 로직을 넣는 것 이 좋다.
    // 데이터를 가지고 있는 곳에서 비즈니스 로직이 나가는게 응집도가 있다.
    // setter 를 통해 세팅하지 말고 비즈니스 로직을 따로 둔다.
    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
