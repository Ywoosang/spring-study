package ywoosang.springjpa.shop.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ywoosang.springjpa.shop.api.domain.Address;
import ywoosang.springjpa.shop.api.domain.Member;
import ywoosang.springjpa.shop.api.service.MemberService;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // 컨트롤러가 view (타임리프) 로 넘어갈때 여기에 데이터를 심어 넘긴다.
        // new MemberForm() 이라는 빈 객체를 가지고 간다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // @Valid 로 form 유효성 검사
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }


    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        if(!members.isEmpty()) {
            for(Member member : members) {
                System.out.println("member = " + member.getAddress());
            }
        }
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
