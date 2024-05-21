package ywoosang.javajpa;

import jakarta.persistence.*;


import java.util.*;

// @Entity 를 넣어야 JPA 가 인식한다.
@Entity
//@Table(uniqueConstraints = )
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ",
        initialValue = 1,
        // DB 에 50개 시퀀스를 가져와 메모리에 미리 올려두고 쓰는 방식이다.
        allocationSize = 50
)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    // 객체에는 username, 데이터베이스 컬럼명은 name 으로 매핑
    // @Column(unique = true) 컬럼을 통해 unique 제약조건을 줄 수도 있지만
    // alter table Member add constraint UK_ektea7vp .. 처럼 이름을 구별하기 힘들게 제약조건이 생긴다.
    // 따라서 운영환경에서는 잘 사용하지 않는다.
    // @Table(uniqueConstraints = ) 를 이용하자.
    @Column(name = "name")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberProduct> memberProducts = new ArrayList<>();

//    // Integer 에 가장 적절한 숫자 타입이 DB 에서 선택되어 만들어진다.
//    private Integer age;
//
//    // Enum 타입을 사용할 경우
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//
//    // 날짜 타입  DATE, TIME, TIMESTAMP;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    // 지금은 자바 8 에서 LocalDate (date 타입), LocalDateTime (timestemp 타입) 을 사용하면 된다.
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    // varchar 를 넘어서는 큰 텍스트를 쓸 때는 Lob 를 사용한다.
//    // 문자 타입인 clob 으로 생성된다.
//    @Lob
//    private String description;
//
//    @Transient // 데이터베이스에는 쓰지 않고 객체(메모리) 에서만 사용하겠다는 뜻이다.
//    private int temp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

//    @Override
//    public String toString() {
//        return "Member{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", team=" + team.getName() +
//                '}';
//    }

    //    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public RoleType getRoleType() {
//        return roleType;
//    }
//
//    public void setRoleType(RoleType roleType) {
//        this.roleType = roleType;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Date getLastModifiedDate() {
//        return lastModifiedDate;
//    }
//
//    public void setLastModifiedDate(Date lastModifiedDate) {
//        this.lastModifiedDate = lastModifiedDate;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
}