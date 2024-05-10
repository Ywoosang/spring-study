package ywoosang.springjpa.shop.domain;


import jakarta.persistence.Embeddable;

// @Embeddable 은 해당 클래스가 다른 엔티티의 일부로 포함될 수 있는 객체를 정의하는데 사용된다.
// 클래스가 독립적인 엔티티로 매핑되지 않고 다른 엔티티의 값 타입으로 포함될 때 사용된다.
// 따라서 데이터베이스 테이블이 직접 생성되지 않으며 다른 엔티티 클래스 내에 포함되어 그 엔티티의 일부 컬럼으로 생성된다.

//  값타입의 좋은 설계는 생성할 때만 값이 세팅이 되야한다.
//  Getter 만제공 하고 Setter 를 아예 제공하지않는 것이다.
//
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    // JPA 가 생성할 때 기본생성자가 있어야 한다.
    // JPA 가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 대 리플렉션 같은 기술을 사용할 수 있도록
    // 지원해야 하기 때문이다.
    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
