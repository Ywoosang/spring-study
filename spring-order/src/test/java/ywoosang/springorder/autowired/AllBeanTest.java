package ywoosang.springorder.autowired;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.AutoAppConfig;
import ywoosang.springorder.discount.DiscountPolicy;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


// 스프링 빈이 생성되거나 소멸하기 전에 메서드를 호출해주는 내용
// 애플리케이션 서버가 올라올 때 TCP/IP 핸드쉐이킹하고 데이터베이스를 연결하는데 시간이 오래 걸리기 때문에 DB 커넥션을 미리 연결해둔다.
// 이때 커넥션 풀을 10개 많으면 100개 열어둔다.
// 고객 요청이 올 때 미리 연결해둔 것을 그대로 재활용할 수 있다.
// 네트워크를 가지고 다른쪽이랑 소켓을 미리 열어둔다.
// 소켓이 뜰 때 소켓을 다 열어놓으면 다음에 고객이 요청이 왔을 때 이미 열려있는 소켓을 가지고 빠르게 응답을 줄 수 있다.
// 애플리케이션이 내려갈 때 데이터베이스 연결도 미리 끊어주고 안전하게 정상적으로 종료처리 되도록 한다.
// 이런 작업들을 스프링이 빈 생명주기 콜백으로 제공한다.


public class AllBeanTest {

    @Test
    void findAllBean() {
        // AutoAppConfig 를 등록하고 그 다음 DiscountService 등록
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService disCountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int  discountPrice = disCountService.discount(member, 10000, "fixDiscountPolicy");

        assertThat(disCountService).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = disCountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(rateDiscountPrice).isEqualTo(2000);

    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            // 빈에 등록된 DiscountPolicy 가 모두 출력된다.
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int amount, String discountCode) {
            // DiscountPolicy 를 policyMap 에서 get 으로 꺼내온다.
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member,amount);
        }
    }
}
