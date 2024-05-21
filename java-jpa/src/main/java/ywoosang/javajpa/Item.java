package ywoosang.javajpa;

import jakarta.persistence.*;


// Book, Album, Movie 엔티티는 Item 을 상속받아 구현되었다.
//create table Item (
//    price integer not null,
//    id bigint not null auto_increment,
//    DTYPE varchar(31) not null,
//    actor varchar(255),
//    artist varchar(255),
//    author varchar(255),
//    director varchar(255),
//    isbn varchar(255),
//    name varchar(255),
//    primary key (id)
//) engine=InnoDB
// JPA 기본 전략이 단일 테이블 전략이므로 Item 테이블에 모두 생성되는 것을 볼 수 있다.


@Entity
//테이블 전략을 명시적으로 지정하면 자식 엔티티가 각각 따로 테이블이 생성된다.
@Inheritance(strategy = InheritanceType.JOINED)
// DTYPE varchar(31) not null 이 추가된다.
// insert
//    into
//        Item (name, price, DTYPE)
//    values
//        (?, ?, 'Movie')
// DTYPE 에 상속받은 엔티티명이 들어간다.
// 자식테이블에 @DiscriminatorValue("movie") 처럼 이름을 명시적으로 지정하면 그 이름으로 들어간다.
// 단일테이블 전략이라면 한 테이블에 다 넣고 DTYPE 으로 구분한다.


// 구현 테이블마다 테이블을 생성하는 전략은 사용하지 말도록 한다.
// 구현클래스마다 테이블을 생성하는 전략을 사용하면 부모클래스로 조회할 때 Item item = em.find(Item.class, movie.getId());
// union 으로 item, movie, album 을 모두 select 한것을 합쳐서 거기서 찾는다.
// 따라서 구현 클래스마다 테이블을 생성하면 비효울적이다.
@DiscriminatorColumn
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
