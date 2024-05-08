package ywoosang.springorder.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.AutoAppConfig;
import ywoosang.springorder.member.MemberService;

import static org.assertj.core.api.Assertions.assertThat;


public class AutoAppConfigTest {
    // Identified candidate component class: file [/Users/ywoosang/projects/spring-study/spring-order/out/production/classes/ywoosang/springorder/discount/RateDiscountPolicy.class]
    // 컴포넌트 스캔할 때 해당 클래스의 후보로 스캔한 것이 나온다.
    // 만약 DiscoundPolicy 를 fix 와 rate 둘 다 등록한다면
    // no qualifying bean of type 'ywoosang.springorder.discount.DiscountPolicy' available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
    // 오류가 발생한다. 하나만 등록해야 자동으로 설정이 가능하다.
    // 같은 타입이 여러개 있으면 충돌이 난다. 타입이 같은 빈을 찾아서 주입하기 때문이다.

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);
    }

}
