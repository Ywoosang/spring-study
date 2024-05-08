package ywoosang.springorder.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        //
        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );
    }


    // @ComponentScan.Filter 를 static import 해서 @Filter 로 사용할 수 있다.
    // type = FilterType.ANNOTATION 는 기본값이므로 생략이 가능하다.
    // ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
    // BeanA 도 제외하려면
    // @Filter(type=FilterType.ASSIGNABLE_TYPE, classes = BeanA.class) 를 추가하면 제외된다.
    // 대부분 @Component 면 충분하기 때문에 includeFilters 를 사용할 일은 거의 없다.
    // excludeFilters 는 여러가지 이유로 간혹 사용할 때가 있지만 많지는 않다.
    // 특히 최근 스프링부트는 컴포넌트 스캔을 기본으로 제공하는데 옵션을 변경하면서 사용하기보다는
    // 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장한다.
    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
            excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
    )
    static class ComponentFilterAppConfig {

    }
}
