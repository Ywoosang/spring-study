package ywoosang.springorder.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ywoosang.springorder.discount.FixDiscountPolicy;
import ywoosang.springorder.member.Grade;
import ywoosang.springorder.member.Member;
import ywoosang.springorder.member.MemoryMemberRepository;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
