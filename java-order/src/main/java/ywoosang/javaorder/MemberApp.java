package ywoosang.javaorder;

import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;
import ywoosang.javaorder.member.MemberService;

public class MemberApp {
    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        // 같은 인스턴스가 출력된다.
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
