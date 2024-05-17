package ywoosang.javajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// @Entity 를 넣어야 JPA 가 인식한다.
@Entity
public class Member {
    @Id
    private Long id;
    private String name;

    // JPA 는 내부적으로 리플렉션 등 기술을 쓰기 때문에 동적으로 객체를 생성할 수 있어야 한다.
    // 따라서 기본 생성자가 있어야 한다.
    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
