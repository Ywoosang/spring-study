package ywoosang.javajpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        // EntityManagerFactory 는 애플리케이션 로딩 시점에 하나만 만들어야 한다.
        // DB 커넥션을 얻어서 쿼리를 날리고 종료되는 트랜잭션 단위는 매번 엔티티 매니저를 만든다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작
        tx.begin();

        try {
            Member member1= new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("======================");

            em.persist(member1); // next_val 1, 51 두 번 호출
            em.persist(member2); // 메모리에서 시퀀스 가져옴
            em.persist(member3); // 메모리에서 시퀀스 가져옴
            // 이후 51번을 만나는 순간 호출

            System.out.println("member1.getId() = " + member1.getId());
            System.out.println("member3.getId() = " + member3.getId());
            System.out.println("member2.getId() = " + member2.getId());

            System.out.println("======================");

            tx.commit();
        } catch (Error e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();






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
