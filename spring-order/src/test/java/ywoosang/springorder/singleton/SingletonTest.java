package ywoosang.springorder.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.AppConfig;
import ywoosang.springorder.member.MemberService;
import ywoosang.springorder.member.MemberServiceImpl;

public class SingletonTest {

    @Test
    @DisplayName("스프링이 없는 순수한 DI 컨테이너")
    void pureContainer() {
        // 스프링 없는 순수한 DI 컨테이너인 AppConfig 는 요청을 할 때마다 객체를 새로 생성한다.
        // 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸되므로 메모리 낭비가 심하다.
        // 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 싱글톤 패턴을 따라 설계한다.
        AppConfig appConfig = new AppConfig();
        // 조회: 호출할때마다 객체를 생성하는 문제점
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();
        // 조회할때마다 memberService 인스턴스가 만들어지는 것을 볼 수 있다.
        // jvm 메모리에 계속 객체가 생성되서 올라가게 된다.
        // memberService1: ywoosang.springorder.member.MemberServiceImpl@62bd765
        // memberService2 = ywoosang.springorder.member.MemberServiceImpl@23a5fd2

        // 참조값이 다른 것을 확인한다.
        System.out.println("memberService1: " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        // 만약 스프링 컨테이너를 사용하면 스프링 컨테이너가 객체를 싱글톤으로 만들어서 관리해준다.
        // 여기서는 직접 싱글톤으로 만들어서 테스트해본 것
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // same: 객체 인스턴스같은 참조를 비교하는 것
        // equal: 자바의 equals 메서드와 같은 비교
        Assertions.assertThat(singletonService1).isSameAs(singletonService2);
    }


    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainerTest() {
        // 스프링 컨테이너가 객체를 싱글톤으로 만들어서 관리
        // 이미 만들어진 memberService 객체를 공유해서 효율적으로 재사용할 수 있다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 =  ac.getBean("memberService", MemberService.class);

        // 참조값이 다른 것을 확인한다.
        System.out.println("memberService1: " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }
}
