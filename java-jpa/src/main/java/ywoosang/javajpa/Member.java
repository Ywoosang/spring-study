package ywoosang.javajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// @Entity 를 넣어야 JPA 가 인식한다.
@Entity
public class Member {
    @Id
    private Long id;
    private String name;

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
