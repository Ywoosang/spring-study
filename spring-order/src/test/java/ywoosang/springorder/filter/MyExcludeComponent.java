package ywoosang.springorder.filter;

import java.lang.annotation.*;

// ElementType.TYPE 를 쓰면 클래스 레벨에 붙는다.
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 애노테이션을 생성한다
// 이 애노테이션이 붙은 것을 컴포넌트 스캔에 추가한다는 의미
public @interface MyExcludeComponent {

}
