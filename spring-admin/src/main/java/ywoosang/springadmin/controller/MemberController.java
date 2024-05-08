package ywoosang.springadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ywoosang.springadmin.domain.Member;
import ywoosang.springadmin.service.MemberService;

import java.util.List;

@Controller
public class MemberController {
    // 스프링 컨테이너가 부팅될 때 컨트롤러 애노테이션이 있으면 이 멤버 컨트롤러 객체를 생성해서 Spring 에 넣어준다.
    // 그러면 Spring 이 관리하게 된다.
    private final MemberService memberService;

    // 컨트롤러가 생성될 때 생성자를 호출하면 Autowired 가 있으면 스프링이 스프링 컨테이너에 있는 멤버 서비스를 가져다 연결을 자동으로 시켜준다.
    // 이게 동작하려면 memberService 를 스프링 빈으로 등록해야 한다.
    // MemberService 위에 @Service 를 넣어준다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create (MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members   = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
