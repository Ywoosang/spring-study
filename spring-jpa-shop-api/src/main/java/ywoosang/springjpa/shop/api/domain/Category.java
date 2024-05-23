package ywoosang.springjpa.shop.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ywoosang.springjpa.shop.api.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // Category 도 List 로 Item 을 가지고
    // Item 도 List 로 Category 를 가진다.
    @ManyToMany
    // 다대다 연관 테이블 지정
    // 반대쪽에는 mappedBy 를 써준다.
    @JoinTable(
            name = "cateogry_item", // 중간 테이블 이름
            joinColumns=@JoinColumn(name="category_id"), // 현재 엔티티의 컬럼
            inverseJoinColumns=@JoinColumn(name="item_id") // 반대쪽 엔티티의 컬럼
    )
    private List<Item> items = new ArrayList<>();

    // 같은 엔티티에 대해 셀프 연관관계
    // 카테고리 구조는 계층구조로 쭉 내려간다.
    // 부모 카테고리에서는 foreign_key 로 자식 카테고리를 찾을 수 있어야 한다.
    // 따라서 foreign_key 가 있는부분에 @JoinColumn 을 써주고
    // child 에는 mappedBy 를 써준다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
