package ywoosang.springorder.beanfind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import ywoosang.springorder.AppConfig;
import ywoosang.springorder.member.MemberService;
import ywoosang.springorder.member.MemberServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertInstanceOf(MemberServiceImpl.class, memberService);
    }

    @Test
    @DisplayName("이름없이 타입으로만 조회")
    void findBeanByType() {
        // 이름을 빼도 정상작동하지만 같은 타입이 여러개 일 경우 문제가 될 수 있다.
        MemberService memberService = ac.getBean(MemberService.class);
        assertInstanceOf(MemberServiceImpl.class, memberService);
    }

    @Test
    @DisplayName("구체 타입으로만 조회")
    void findBeanByImplType() {
        // 스프링빈에 등록된 인스턴스화된 인스턴스 타입을 보고 결정하기 때문에
        // 꼭 인터페이스 타입이 아니어도 된다.
        // 구체 타입으로 조회하면 유연성이 떨어진다.
        // 역할과 구현을 분리하는 관점에서는 인터페이스 타입을 적는 것이 더 올바르다.
        MemberService memberService = ac.getBean("memberService",MemberServiceImpl.class);
        assertInstanceOf(MemberServiceImpl.class, memberService);
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xx", MemberServiceImpl.class)
        );
    }


}