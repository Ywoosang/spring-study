package ywoosang.springorder.common;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

// 스프링 애플리케이션을 실행시키면 오류가 발생한다.
// Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton
// 스프링 애플리케이션을 실행하는 시점에서 싱글톤 빈은 생성해서 주입이 가능하지만
// request 스코프 빈은 아직 생성되지 않기 때문
// 이럴때 Provider 로 해결할 수 있다.
@Component
@Scope(value= "request")
public class MyLogger {
    // uuid 로 같은 사용자가 보낸 요청인지 구분한다.
    // url 은 생성 시점에 넣어주도록 한다.
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }
    
    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);

    }

    // 처음 고객 요청이 왔을 때 호출한다.
    // 빈은 http 요청 당 하나씩 새로 생성되기 때문에 uuid 를 넣어두면 다른 http 요청과 구뱔할 수 있다.
    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    // 고객 요청이 끝날을 때 호출한다.
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
