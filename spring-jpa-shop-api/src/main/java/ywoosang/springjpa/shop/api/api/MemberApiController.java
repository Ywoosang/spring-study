package ywoosang.springjpa.shop.api.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ywoosang.springjpa.shop.api.domain.Member;
import ywoosang.springjpa.shop.api.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;

//@Controller 와 @ResponseBody 를 합친 애노테이션이다.
// @ResponseBody 는 응답을 바로 json 이나 xml 로 보낼 때 사용한다.
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> getAllMembers() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect =  findMembers
                .stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect);
    }


    // Object 타입으로 반환하는 것이기 때문에 result 라는 껍데기를 씌워준다.
    // data 필드의 값은 List 등 담은 값으로 나온다.
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    // List 를 바로 반환하면 json 배열 타입으로 나가기 때문에 유연성이 떨어진다.
    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }



    // presentation 화면 validation 로직이 엔티티에 들어가 있으면 안된다.
    // 엔티티를 바꾸면 api 스펙이 변하게 된다.
    // api 스펙을 위한 별도 DTO 를 만들어야 한다.
    // api 를 만들 때는 엔티티를 파라미터로 받지 말고 외부에 응답으로 노출해서도 안된다.
    @PostMapping("/api/v1/members")
    // json 으로 온 데이터를 member 로 바꿔준다.
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(),request.getName());
    }

    // path variable 의 id 로 사용자를 찾아서 dto 에 들어온 이름으로 변경
    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;

    }



    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
