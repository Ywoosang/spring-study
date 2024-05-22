package ywoosang.javajpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

public class JpaMain {

    public static void main(String[] args) {

        // EntityManagerFactory 는 애플리케이션 로딩 시점에 하나만 만들어야 한다.
        // DB 커넥션을 얻어서 쿼리를 날리고 종료되는 트랜잭션 단위는 매번 엔티티 매니저를 만든다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildren().remove(0);
            // child1, child2 도 persist 를 해줘야 한다.
            // persist 를 하지 않으면 parent 만 insert 된다.
//            insert
//                    into
//            Parent (name)
//            values
//                    (?)
            // parent 를 persist 할 때 child 도 자동으로 persist 해주려면 cascade =ALL 로 설정하면 된다.
//            @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
//            private List<Child> childList = new ArrayList<>();
//            insert
//                    into
//            Child (name, parent_id)
//            values
//                    (?, ?)
            // child 도 persist 된 것을 확인할 수 있다.
            // Parent 를 persist 할 때 cascade 를 설정한 곳 아래 있는 childList 도 persist 시켜주는 옵션이 cascade 다.
            // cascade 는 연쇄라는 의미다.





//            em.persist(child1);
//            em.persist(child2);

//            Team team = new Team();
//            team.setName("팀2");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            List<Member> members = em.createQuery("from Member", Member.class).getResultList();
//
//
//
//
//

            // em.getReference()를 통해 프록시 객체 조회
//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember class: " + refMember.getClass());
//
//            // em.find()를 통해 실제 엔티티 조회
//            Member findMember = em.find(Member.class, member.getId());
//            System.out.println("findMember class: " + findMember.getClass());
//
//            System.out.println("refMember == findMember: " + (refMember == findMember));
//
//            // instanceof 비교 (성공)
//            System.out.println("refMember instanceof Member: " + (refMember instanceof Member));
//
//            // equals() 비교 (성공)
//            System.out.println("refMember.equals(findMember): " + refMember.equals(findMember));


//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember.getClass() = " + refMember.getClass()); // Proxy
//
//            em.detach(refMember);
//
//            refMember.getUsername();
            // org.hibernate.LazyInitializationException: could not initialize proxy [ywoosang.javajpa.Member#1] - no Session


//            Member findMember = em.getReference(Member.class, member2.getId());
//            System.out.println("findMember.getClass() = " + findMember.getClass());
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getUsername() = " + findMember.getUsername());


            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

//        try {
//            Member member = new Member() ;
//            member.setCreatedBy("우상");
//            member.setUsername("테스트사용자");
//            member.setCreatedDate(LocalDateTime.now());
//            tx.commit();
//        } catch(Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            Movie movie = new Movie();
//            movie.setDirector("aaaa");
//            movie.setActor("bbbb");
//            movie.setName("이름");
//            movie.setPrice(10000);
//
//            em.persist(movie);
//
//            em.flush();
//            em.clear();
//
//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);
//            tx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        } finally {
//            em.close();
//        }
//        emf.close();


//        트랜잭션 시작
//        tx.begin();
//        try {
////
////            Member member = new Member();
////            member.setUsername("member1");
////            em.persist(member);
////
////            Team team = new Team();
////            team.setName("TeamA");
////            양방향 매핑시 가장 많이 하는 실수는 연관관계의 주인에 값을 입력하지 않는 것이다.
////            연관관계의 주인이 아닌 team 에서 member 를 추가하고 persist 하면
////            member 의 foreign_key 인 team_id 가 업데이트되지 않는다.
////            mysql> select * from member;
////            +----+---------+---------+
////            | id | team_id | name    |
////            +----+---------+---------+
////            |  1 |    NULL | member1 |
////            +----+---------+---------+
////            team.getMembers().add(member);
////            em.persist(team);
//
//
//            // 순수한 객체관계를 고려해 작성 하려면 양쪽에 값을 다 세팅해야 한다.
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team);
//            em.persist(member);
//
//            em.flush();
//            em.clear();
//
//            Team findTeam = em.find(Team.class, team.getId());
//
//
//            // team.getMembers().add(member) 를 해주지 않아도 값이 들어가 있는 것을 볼 수 있다.
//            // JPA 에서 members 의 데이터를 사용하는 시점에 쿼리를 보낸다.
//            List<Member> members = findTeam.getMembers();
//            for (Member m : members) {
//                System.out.println("m.getUsername() = " + m.getUsername());
//            }
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();

//        try {
//            Member member1= new Member();
//            member1.setUsername("A");
//
//            Member member2 = new Member();
//            member2.setUsername("B");
//
//            Member member3 = new Member();
//            member3.setUsername("C");
//
//            System.out.println("======================");
//
//            em.persist(member1); // next_val 1, 51 두 번 호출
//            em.persist(member2); // 메모리에서 시퀀스 가져옴
//            em.persist(member3); // 메모리에서 시퀀스 가져옴
//            // 이후 51번을 만나는 순간 호출
//
//            System.out.println("member1.getId() = " + member1.getId());
//            System.out.println("member3.getId() = " + member3.getId());
//            System.out.println("member2.getId() = " + member2.getId());
//
//            System.out.println("======================");
//
//            tx.commit();
//        } catch (Error e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();


//        try {
//            Member member1 = new Member(150L,"A");
//            Member member2 = new Member(160L,"B");
//
//            em.persist(member1);
//            em.persist(member2);
//
//            System.out.println(" ======================= ");
//
//            tx.commit();
//        } catch (Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
//        emf.close();



//        try {
//            // JPA 에서 데이터를 변경하는 모든 작업은 트랜잭션 안에서 작업을 해야 한다.
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("hello");
//
//            em.persist(member);
//
//            // persist 이후에 조회가 가능하다.
//            // memberId= 1
//            // memberName= hello
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("memberId= " + findMember.getId());
//            System.out.println("memberName= " + findMember.getName());
//
//
//            // 트랜잭션이 commit 되지 않으면  커넥션이 반환되지 않아 연결 누수가 발생할 수 있다.
//            // 트랜잭션 커밋을 누락하면 트랜잭션이 완료되지 않아서 데이터베이스 커넥션이 트랜잭션 상태를 유지한 채로 남게 된다.
//            // 변경 사항 미반영: 데이터베이스에 대한 변경 사항이 커밋되지 않아 반영되지 않는다.
//            // 커넥션 반환 지연: 트랜잭션이 종료되지 않아서 커넥션이 풀에 반환되지 않습니다. 이는 커넥션 누수로 이어질 수 있다.
//            tx.commit();
//
//        } catch(Exception e) {
//            tx.rollback();
//            e.printStackTrace();
//        } finally {
//            // 엔티티 매니저가 내부적으로 데이터베이스 커넥션을 가지고 동작하기 때문에, 트랜잭션이 끝났으면 닫아줘야 한다.
//            // 엔티티 매니저를 닫지 않으면 엔티티 매니저가 사용 중인 모든 리소스, 특히 데이터베이스 커넥션이 반환되지 않는다.
//            // 커넥션 누수: 엔티티 매니저가 닫히지 않아서 관련된 데이터베이스 커넥션이 풀에 반환되지 않고 누수로 남는다.
//            // 리소스 낭비: 메모리와 같은 다른 리소스도 회수되지 않아서 낭비가 발생한다.
//            em.close();
//        }
//        emf.close();
    }
}
