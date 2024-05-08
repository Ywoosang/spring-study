package ywoosang.springorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;
import ywoosang.springorder.member.MemberService;
import ywoosang.springorder.order.Order;
import ywoosang.springorder.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {

        // ApplicationContext 를 스프링 컨테이너라고 한다.
        // 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다.
        // 여기서 @Bean 이라고 적힌 메서드를 모두 호출해 반환된 객체를 스프링 컨테이너에 등록한다.
        // 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.
        // 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.
        // 특별히 @Bean(name= ) 으로 이름을 지정할 수 있다.
        // 스프링 빈은 applicationContext.getBean() 메서드를 사용해서 찾을 수 있다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order = " + order);
    }
}
