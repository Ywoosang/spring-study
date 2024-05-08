package ywoosang.javaorder.discount;

import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;

public class FixDiscountPolicy implements DiscoumtPolicy{

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
