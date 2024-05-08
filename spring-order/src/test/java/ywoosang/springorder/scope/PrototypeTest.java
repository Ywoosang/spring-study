package ywoosang.springorder.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

// 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행되지만, 프로토타입 스코프의 빈은 스프링 컨테이너에서
// 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.
// 프로토타입 빈을 2번 조회했으므로, 완전히 다른 다른 스프링 빈이 생성되고, 초기화도 2번 실행된다.
// 싱글톤 빈은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될 때 빈의 종료 메서드가 실행되지만,
// 프로토타입 빈은 스프링 컨테이너가 생성과 의존관계 주입, 초기화까지만 관여하고 더는 관리하지 않는다.
// 따라서 프로토타입 빈은 스프링 컨테이너가 종료될 때 @PreDestroy 같은 종료 메서드가 전혀 실행되지 않는다.
// 프로토타입 빈을 조회한 클라이이언트가 종료 메서드를 호출해야 한다.
public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        // AnnotationConfigApplicationContext 에 class 로 넘기면 @Component 가 없어도 빈에 등록한다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();

//        결과
//        find prototypeBean1
//        PrototypeBean.init  조회할 때 생성된다.
//        find prototypeBean2
//        PrototypeBean.init
//        prototypeBean1 = ywoosang.springorder.scope.PrototypeTest$PrototypeBean@525575
//        prototypeBean2 = ywoosang.springorder.scope.PrototypeTest$PrototypeBean@46dffdc3
    }
    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
