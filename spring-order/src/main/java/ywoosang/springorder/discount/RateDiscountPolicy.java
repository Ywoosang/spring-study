package ywoosang.springorder.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ywoosang.springorder.annotation.MainDiscountPolicy;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;

@Component
@Primary
//@Qualifier("mainDiscountPolicy")
//@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private final int discountPrecent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return price * discountPrecent / 100;
        } else {
            return 0;
        }
    }
}
