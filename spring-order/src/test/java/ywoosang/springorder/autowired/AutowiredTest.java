package ywoosang.springorder.autowired;


import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.member.Member;
import ywoosang.springorder.member.MemberService;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }


    static class TestBean {
        
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            // 출력이 되지 않는 것을 볼 수 있음
            // 의존관계가 없으면 이 메서드 자체가 호출이 되지 않기 때문이다.
            System.out.println("noBean1 = " + noBean1);
        }

        @Autowired(required = false)
        public void setNoBean2(@Nullable Member noBean2) {
            // 호출은 되었지만 null 이 출력된다.
            // required = false 를 붙여도 @Nullable 이 있기 때문에 호출되는 것이다.
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired(required = false)
        public void setNoBean3(Optional<Member> noBean3) {
            // 호출은 되었지만 Optional.empty 가 출력된다.
            // required = false 를 붙여도 Optional 이 있기 때문에 호출되는 것이다.
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
