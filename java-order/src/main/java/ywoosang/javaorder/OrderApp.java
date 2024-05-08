package ywoosang.javaorder;

import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;
import ywoosang.javaorder.member.MemberService;
import ywoosang.javaorder.order.Order;
import ywoosang.javaorder.order.OrderService;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();
        appConfig.orderService();
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order = " + order);
    }
}
