package ywoosang.springorder.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        // singleton, prototype 둘 다 등록
        // 먼저 singleton 빈이 등록되면서 생성자 autowired (롬북을 사용) 에서 PrototypeBean 을 스프링 컨테이너에게 요구한다.
        // 스프링 컨테이너가 PrototypeBean 을 만들어서 전달한다.
        // 그때 만들어진 PrototypeBean 이 private final 로 할당된다.
        // 따라서 생성시점에 주입했기 때문에 로직을 호출해도 같은 빈이 제공된다.
        // 프로토타입 빈을 주입시점에만 생성하는게 아니라, 사용할 때 마다 새로 생성해서 사용하는 것을 원할 것이다.
        // ObjectProvider 의 DL (Dependency look up) 을 이용하면 이를 해결할 수 있다.
        AnnotationConfigApplicationContext ac
                = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean ClientBean2 = ac.getBean(ClientBean.class);
        int count2 = ClientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean;

        // Autowired 로 주입해줘야 null 이 안나온다.
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;


//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic () {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean destroy" + this);
        }
    }
}
