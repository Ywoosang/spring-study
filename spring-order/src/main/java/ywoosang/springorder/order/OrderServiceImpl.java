package ywoosang.springorder.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ywoosang.springorder.discount.DiscountPolicy;
import ywoosang.springorder.member.Member;
import ywoosang.springorder.member.MemberRepository;

@Component
// cmd + fn + f12 눌러서 확인해보면 생성자가 있는 것을 볼 수 있다.
// 롬복에서 만들어준다.
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 할인 정책을 변경하려면 이 코드를 변경해야 한다.
    // 할인 정책에 대해서 역할과 구현을 분리했다 O
    // 다형성을 활용하고, 인터페이스와 구현 객체를 분리했다 O

    // OCP, DIP 같은 객체지향 원칙을 충실히 준수한 것 같지만 사실은 아니다.
    // DIP: 구체화에 의존하지 않고 추상화에 의존해야 한다.
    // - 주문 서비스 클라이언트 OrderServiceImpl 은 DiscountPolicy 인터페이스에 의존하면서 DIP 를 지킨 것 같지만
    // - 클래스 의존관계를 분석해보면, 추상(인터페이스) 뿐만 아니라 구체 (구현) 클래스에도 의존하고 있다.
    // - DiscountPolicy 인터페이스와 FixDiscountPolicy, RateDiscountPolicy 에도 의존하고 있기 때문에다.
    // OCP: 변경에는 닫혀 있고 확장에는 열려 있어야 한다.
    // - 지금 코드는 기능을 확장해서 변경하면 클라이언트 코드 OrderServiceImpl 에서 구현 클래스를 변경해줘야 한다.


    //  FixDiscountPolicy 를 RateDiscountPolicy 로 변경하는 순간
    //  OrderServiceImpl 의 소스코드도 함께 변경해야 한다.
    //  즉, 구체화에도 의존하고 있기 때문에 OCP 를 위반한다.
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    // 추상화에만 의존하도록 코드를 변경한다.
    // 관심사의 분리
    // - 구현체가 없는데 코드를 실행하려면 클라이언트인 OrderServiceImpl 에 DiscountPolicy 의 구현 객체를
    // - AppConfig 가 대신 생성하고 주입해주어야 한다.
    // - OrderServiceImpl 은 구현체를 직접 생성하고 연결할 책임을 지지 않고 AppConfig 가 이를 대신 수행한다.

    // final 키워드를 넣어주면 생성자에서 혹시라도 값이 생성되지 않는 오류를 컴파일 시점에 막아준다.
    // 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로
    // 필드에 final 키워드를 사용할 수 없다.
    // 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    롬복에서 자동으로 만들어준다.
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//        this.memberRepository = memberRepository;
//    }

    // SRP 를 지키기 위해 OrderService 에서 할인에 관한건 discountPolicy 에 위임해 결과만 받아오도록 설계한 것이다.
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 회원정보를 조회한다.
        Member member = memberRepository.findById(memberId);
        // 할인정책을 넘긴다.
        int discountPrice = discountPolicy.discount(member, itemPrice);

        // 주문을 생성해 반환한다.
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
