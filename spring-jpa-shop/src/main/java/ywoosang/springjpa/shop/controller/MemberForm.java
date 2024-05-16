package ywoosang.springjpa.shop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    // javax validation 을 통해 값이 비어있으면 오류를 반환
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
