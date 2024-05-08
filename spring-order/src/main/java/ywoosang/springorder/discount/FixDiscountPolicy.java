package ywoosang.springorder.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;

@Component
//@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{

    private final int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
