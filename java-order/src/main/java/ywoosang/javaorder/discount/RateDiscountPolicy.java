package ywoosang.javaorder.discount;

import ywoosang.javaorder.member.Grade;
import ywoosang.javaorder.member.Member;

public class RateDiscountPolicy implements DiscoumtPolicy{

    private int discountPrecent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return price * discountPrecent / 100;
        } else {
            return 0;
        }
    }
}
