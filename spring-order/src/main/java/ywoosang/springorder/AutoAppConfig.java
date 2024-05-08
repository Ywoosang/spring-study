package ywoosang.springorder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 컴포넌트 스캔으로 자동으로 등록해주는데 그중에서 제외할 것을 설정한다.
        // Configuration 애노테이션이 붙은 것은 자동 빈 등록을 해제한다.
        // 기존에 만들었던 AppConfig 에서 수동으로 등록하는데, 컴포넌트 스캔으로 자동 등록할 것이므로, 이 부분을 빼줘야 한다.
        // Configuration 애노테이션에는 @Component 가 있으므로 자동으로 빈에 컴포넌트 스캔에 의해 등록되는 대상이 된다.
        // @Configuration 이 붙은 설정정보도 자동으로 등록되기 때문에 AppConfig, TestConfig 등 앞에 만들어 두었던 설정 정보도 함께 등록되고 실행되어 버린다.


        // 컴포넌트 스캔 위치를 명시적으로 지정하지 않으면, @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        // 패키지 위치를 지정하지 않고 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것을 자주 사용한다.
        // 이 위치부터 컴포넌트 스캔을 한다고
        // basePackages = "ywoosang.springorder.member",

        // AutoAppConfig.class 가 있는 패키지 즉 ywoosang.springorder 부터 찾는다.
        // basePackageClasses = AutoAppConfig.class,

        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)

// AutoAppConfig 는 비어 있다.
// 의존 관계를 자동으로 주입해야 하기 때문에 @Autowired 를 붙여줘야 한다.
public class AutoAppConfig {

}
