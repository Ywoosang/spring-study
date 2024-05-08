package ywoosang.javaorder.discount;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 는 10% 할인이 적용되어야 한다.")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);

        int discount = discountPolicy.discount(member, 10000);

        assertEquals(1000, discount);
    }

    @Test
    @DisplayName("VIP  가 아니면 할인이 적용되지 않아야 한다.")
    void vip_x() {
        // given
        Member member = new Member(1L, "memberVIP", Grade.BASIC);
        // when
        int discount = discountPolicy.discount(member, 10000);
        //
        assertEquals(0, discount);
    }
}