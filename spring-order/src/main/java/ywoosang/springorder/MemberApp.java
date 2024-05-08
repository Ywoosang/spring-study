package ywoosang.springorder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;
import ywoosang.springorder.member.MemberService;

public class MemberApp {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // (이름, 타입)
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        // 같은 인스턴스가 출력된다.
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
