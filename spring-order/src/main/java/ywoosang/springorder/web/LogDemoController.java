package ywoosang.springorder.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ywoosang.springorder.common.MyLogger;

// 로거가 잘 작동하는지 확인하는 테스트용 컨트롤러다
// 여기서 HttpServletRequest 를 통해서 요청 URL 을 받는다.
// 이렇게 받은 requestURL 값을 myLogger 에 저장해둔다. myLogger 는 HTTP 요청 당 각각 구분되므로
// 다른 HTTP 요청 때문에 값이 섞이는 걱정은 하지 않아도 된다.
// 컨트롤러에서 controller test 라는 로그를 남긴다.
// 원래 requestURL 을 MyLogger 에 저장하는 부분은 컨트롤러보다는 공통 처리가 가능한
// 스프링 인터셉터나 서블릿 필터 같은 곳을 활용하는 것이 좋다.

// 컨트롤러의 고객의 요청이 오면 프로바이더가 MyLogger 를 가져온다.
@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    // MyLogger 를 주입받는게 아니라 MyLogger 를 찾을 수 있는 프로바이더가 주입된다. (DL)
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    // HttpServletRequest 는 자바에서 제공하는 표준 서블릿 규약에 의한 http 요청 정보를 받을 수 있다.
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
