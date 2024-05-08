package ywoosang.springorder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ywoosang.springorder.discount.DiscountPolicy;
import ywoosang.springorder.discount.RateDiscountPolicy;
import ywoosang.springorder.member.MemberRepository;
import ywoosang.springorder.member.MemberService;
import ywoosang.springorder.member.MemberServiceImpl;
import ywoosang.springorder.member.MemoryMemberRepository;
import ywoosang.springorder.order.OrderService;
import ywoosang.springorder.order.OrderServiceImpl;

// XML 기반, 애노테이션 이반으로 만들 수 있다.
// 요즘에는 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만드는게 추세다.
// 최상위에는 BeanFactory 가 있고 그 아래 ApplicationContext 가 있다.
// BeanFactory 를 직접 사용하는 경우는 거의 없으므로 일반적으로 ApplicationContext 를 스프링 컨테이너라고 한다.
// new AnnotationConfigApplicationContext(AppConfig.class);
// 위 클래스는 ApplicationContext 인터페이스의 구현체다.
// 빈의 이름은 충돌이 안나게 무조건 다른 이름으로 부여한다.
// 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입한다.
// 스프링은 빈을 생성하고, 의존관계를 주입하는 단계가 나누어져 있다.


// @Bean memberService -> new MemoryMemberRepository()
// @Bean orderService -> new MemoryMemberRepository()
// new MemoryMemberRepository 가 2번 생성되었으므로 싱글톤이 깨지는게 아닌가? 생각할 수 있다.
// 스프링 컨테이너는 싱글톤을 보장해주는데, 이게 어떻게 동작할까?
// 결론적으로 new MemoryMemberRepository() 는 1번만 호출된다.
// 스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해주어야 한다.
// 자바 코드를 보면 분명 3번 호출되어야 하는 것이 맞다.
// call AppConfig.memberService
// call AppConfig.memberRepository // 1
// call AppConfig.memberService
// call AppConfig.memberRepository // 2
// call AppConfig.memberService
// call AppConfig.memberRepository // 3
// call AppConfig.orderService
// 하지만 실제로 찍어보면 1번만 호출된다.
// 그래서 스프링은 클래스와 바이트코드를 조작하는 라이브러리를 사용한다.
// @Configuration 을 적용한 AppConfig 를 test 코드에서 찍어보면
// ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
// AppConfig bean = ac.getBean(AppConfig.class);
// System.out.println(bean.getClass());
// class ywoosang.springorder.AppConfig$$EnhancerBySpringCGLIB$$bd47970 처럼
// $$EnhancerBySpringCGLIB$$ 가 붙어 있다.
// 순수한 클래스라면 class ywoosang.springorder.AppConfig 같이 출력되어야 한다.
// 이것은 스프링이 내가 만든 클래스가 아니라 CGLIB 이라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를
// 상속받은 임의의 다른 클래스를 만들었고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
// @Bean 이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로
// 등록하고 반환하는 코드가 동적으로 만들어지기 때문에 싱글톤이 보장되는 것이다.
// AppConfig@CGLIB 는 AppConfig 의 자식타입이므로 AppConfig 타입으로 조회할 수 있다.

// @Configuration 을 붙이면 바이트코드를 조작하는 CGLIB 기술을 사용해서 싱글톤을 보장하지만 만약 @Bean 만 적용한다면
// 스프링빈에는 모두 정상적으로 등록된다 또한 순수한 AppConfig 빈에 등록된다.
// 하지만 memberRepository 가 3번 new 로 만들어진다. 따라서 싱글톤이 깨진다.
// 또한 new MemberServiceImpl(memberRepository()); 또한 (new memberRepository()) 를 호출한 것이
// 되어 버린다. 스프링 컨테이너가 관리하지 않게 된다.
// 빈에 등록한걸 의존관계 주입하고 이것을 끌어 쓰려면 @Autowired 를 써야 한다.



@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        // 생성자 주입
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    @Primary
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


}
