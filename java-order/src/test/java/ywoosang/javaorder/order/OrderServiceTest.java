package ywoosang.javaorder.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ywoosang.javaorder.AppConfig;
import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;
import ywoosang.javaorder.member.MemberService;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    // 각 @Test 를 실행하기 전 무조건 실행
    // 테스트 메소드가 실행될 때마다 새로운 인스턴스의 memberService와 orderService를 생성하여 사용함으로써, 테스트 간의 상호 영향을 방지한다.
    // 테스트의 독립성을 보장하며, 한 테스트에서 발생한 문제가 다른 테스트에 영향을 미치지 않도록 한다.
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        // primitive 타입 long 으로 하면 null 이 들어갈 수 없기 때문에 wrapper 타입인 Long 을 사용한다.
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertEquals(order.getDiscountPrice(), 1000);
    }
}
