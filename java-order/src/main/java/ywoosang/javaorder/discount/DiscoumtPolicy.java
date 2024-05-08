package ywoosang.javaorder.discount;

import ywoosang.javaorder.member.Member;

public interface DiscoumtPolicy {
    /**
     *
     * @param member
     * @param price
     * @return 할인 대상 금액 (얼마가 할인 되었는지)
     */
    int discount(Member member, int price);

}
