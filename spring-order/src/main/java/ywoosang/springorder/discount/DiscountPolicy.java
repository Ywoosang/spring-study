package ywoosang.springorder.discount;

import ywoosang.springorder.member.Member;

public interface DiscountPolicy {
    /**
     *
     * @param member
     * @param price
     * @return 할인 대상 금액 (얼마가 할인 되었는지)
     */
    int discount(Member member, int price);

}
