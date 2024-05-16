package ywoosang.springjpa.shop.controller;

import lombok.Getter;
import lombok.Setter;

// 엔티티를 직접 등록과 수정 화면에 사용하지 않는다.
// 화면 요구사항이 복잡해지기 시작하면 엔티티에 화면을 처리하기 위한 기능이 점점 증가한다.
// 엔티티는 점점 화면에 종속적으로 벼하고, 이렇게 화면 기능 때문에 지저분해진 엔티티는 유지보수하기 어려워진다.
// 엔티티는 핵심 비즈니스 로직만 가지고 있고, 화면을 위한 로직은 없어야 한다.
// 화면이나 API 에 맞는 폼 객체나 DTO 를 사용해야 한다.
@Getter @Setter
public class BookForm {
    private Long id;
    private String name;
    private String author;
    private String isbn;
    private int price;
    private int stockQuantity;
}
