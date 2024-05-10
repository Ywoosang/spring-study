package ywoosang.springjpa.shop.domain.item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ywoosang.springjpa.shop.domain.Category;

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

}
