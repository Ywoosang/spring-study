package ywoosang.javaorder;

import ywoosang.javaorder.discount.DiscoumtPolicy;
import ywoosang.javaorder.discount.RateDiscountPolicy;
import ywoosang.javaorder.member.MemberRepository;
import ywoosang.javaorder.member.MemberService;
import ywoosang.javaorder.member.MemberServiceImpl;
import ywoosang.javaorder.member.MemoryMemberRepository;
import ywoosang.javaorder.order.OrderService;
import ywoosang.javaorder.order.OrderServiceImpl;

public class AppConfig {
    // 제어 역전 원칙 IOC
    // 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 의미한다.
    // IOC 컨테이너 (DI 컨테이너)
    // AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해주는 것을
    // IOC 컨테이너 또는 DI 컨테이너라고 한다.
    // 의존관계 주입에 초점을 맞춰 최근에는 DI 컨테이너라고 한다.
    // 또는 어샘블러, 오브젝트 팩토리 등으로 불리기도 한다.


    // AppConfig 는 생성한 객체 인스턴스의 참조(래퍼런스) 를 생성자를 통해 주입해준다.
    // 의존 관계에 대한 고민은 외부(AppConfig) 에 맡기고 실행에만 집중한다.
    // 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확히 분리되었다.
    // 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 외부에서 주입해주는 것과 같다고 해서 DI 라고 한다.
    public MemberService memberService() {
        // 생성자 주입
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discoumtPolicy());
    }


    // 각 역할을 분명히 나타내기고 애플리케이션의 전체 구성을 한눈에 보기 위해 별도 메서드로 변경
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public DiscoumtPolicy discoumtPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }


}
