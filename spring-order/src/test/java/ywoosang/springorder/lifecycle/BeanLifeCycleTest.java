package ywoosang.springorder.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        // context 를 닫는 메서드는 잘 쓰지 않기 때문에 직접 ApplicationContext 에서 제공해주지 않는다.
        // ApplicationContext 타입으로 선언한 ac 를 AnnotationConfigApplicationContext 로 바꾸거나
        // ConfigurableApplicationContext 를 사용한다.
        ac.close();
    }


    @Configuration
    static class LifeCycleConfig {

//        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            // 스프링 빈은 객체 생성 -> 의존관계 주입 (생성자 주입을 제외한 나머지)
            // 따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다. 개발자가 의존관계 주입이 모두 완료된 시점을 알려면
            // 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
            // 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.
            // 따라서 안전하게 종료 작업을 진행할 수 있다.
            // 스프링 빈의 이벤트 라이프 사이클
            // 스프링 컨테이너 생성 -> 스프링 빈 생성 (생성자 인젝션) -> 의존관계 주입 (setter, 필드 인젝션) -> 초기화 콜백 -> 사용 -> 소멸 전 콜백 -> 스프링 종료
            // 초기화 콜백 : 빈이 생성되고 빈의 의존관계 주입이 완료된 후 호출된다.
            // 소멸 전 콜백 : 빈이 소멸되기 직전에 호출된다.

            // 객체의 생성과 초기화를 분리하자.
            // 생성자는 필수 정보 (파라미터) 를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
            // 반면에 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행한다.
            // 따라서 생성자 안에서 무거운 초기화 작업을 함꼐 하는 것보다는 객체를 생성하는 부분과 초가화하는 부분을 명확하게 나누는 것이
            // 유지보수 관점에서 좋다.
            // 물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단순한 경우에는 생성자에서 한꺼번에 다 처리하는게 더 나을 수 있다.
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
