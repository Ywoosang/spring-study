package ywoosang.springorder.beanfind;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ywoosang.springorder.member.MemberRepository;
import ywoosang.springorder.member.MemoryMemberRepository;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameBeanFindTest {
    AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByTypeDuplicate() {
        // class 타입으로 조회 시 같은 클래스 타입이 존재하기 때문에 에러가 발생한다.
        assertThrows(NoUniqueBeanDefinitionException.class, () ->
                ac.getBean(MemberRepository.class)
        );
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.")
    void findBeanByName() {
        MemberRepository memberRepository = ac.getBean("memberRepository1", MemberRepository.class);
        assertInstanceOf(MemberRepository.class, memberRepository);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType() {
        // getBeansOfType 은 Map 으로 나온다.
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "value = " + beansOfType.get(key));
        }
        System.out.println("beansOfType = " + beansOfType);
        // 등록해둔 두 개 Bean 이 나와야 한다.
        assertEquals(2, beansOfType.size());
    }

    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

}
