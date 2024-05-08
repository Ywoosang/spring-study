package ywoosang.springadmin;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ywoosang.springadmin.aop.TimeTraceAop;
import ywoosang.springadmin.repository.*;
import ywoosang.springadmin.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // application.properties 에 적어준 설정대로 dataSource 를 스프링에서 빈에 등록해준다.
    // Autowired 를 이용해 주입해 사용하면 된다.

    // jdbc 를 이용하는 경우
//    private final DataSource dataSource;

//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // jpa 를 이용하는 경우
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    private final MemberRepository memberRepository;
    // 스프링 데이터 JPA 가 구현체를 만들어놓은게 등록된다.
    @Autowired // 생략 가능
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // Controller 의 경우 @Autowired 를 사용해 빈에 등록한 memberService 를 컴포넌트스캔을 이용해 자동으로 주입받는다.
    // 수동으로 빈에 등록
    @Bean
    public MemberService memberService() {

//        return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//        // 데이터베이스 연결을 하게 되면 MemoryMemberRepository 를 DbMemberRepository 로 바꿔주기만 하면 된다.
//        // 컴포넌트 스캔을 사용하면 여러 코드를 바꿔야 한다.
//        // return new MemoryMemberRepository();
//        // return new JdbcMemberRepository(dataSource);
//        // return new JdbcTemplateMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
//        return new SpringDataJpaMemberRepository();
//    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

}
