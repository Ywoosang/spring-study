package ywoosang.springjpa.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// 롬복을 이용하면 @Slf4j 로 log 를 바로 사용할 수 있다.
@Slf4j
public class HomeController {
    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}



