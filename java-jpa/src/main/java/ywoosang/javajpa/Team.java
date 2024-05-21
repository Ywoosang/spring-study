package ywoosang.javajpa;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // 테이블은 외래키 하나로 양방향 연관관계를 가진다 (양쪽으로 조인할 수 있다.)
    // 객체는 연관관계를
    // DB 에서는 외래키 값만 업데이트 되면 된다.
    // Team 의 members 를 업데이트할때 외래키를 업데이트할지
    // Members 의 team 을 업데이트할때 외래키를 업데이트할지
    // 둘중 하나를 연관관계의 주인으로 설정해야 한다.
    // 주인이 아닌 쪽은 읽기만 가능해야 한다.
    // 주인이 아니면 mappedBy 속성으로 주인을 지정해야 한다.
    // 연관관계 주인은 외래키가 있는 곳으로 정한다.
    @OneToMany(mappedBy = "team")
    // new ArrayList 로 초기화해두면 add 할 때 NullPointException 이 뜨지 않아서 초기화해두는게 관례다.
    private List<Member> members = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }
//
//    public void setMembers(List<Member> members) {
//        this.members = members;
//    }
//
//    @Override
//    public String toString() {
//        return "Team{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", members=" + members +
//                '}';
//    }
}
