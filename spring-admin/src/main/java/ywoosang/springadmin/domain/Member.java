package ywoosang.springadmin.domain;
import jakarta.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // DB 에 있는 컬럼명과 매핑
    @Column(name = "username")
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
