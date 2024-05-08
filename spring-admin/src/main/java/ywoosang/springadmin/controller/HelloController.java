package ywoosang.springadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        // resources/templates 에 있는 hello.html 를 찾아서 렌더링한다.
        return "hello";
    }

    @GetMapping("hello-mvc")
    // required 는 true 가 기본이다.
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-mvc";
    }

    @GetMapping("hello-string")
    // http 통신에서 body 부분에 return 한 내용을 직접 넣어주겠다는 의미다.
    @ResponseBody
    public String helloString(@RequestParam(value = "name", required = false) String name) {
        return "hello" + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    // Mac 에서 command + n 으로 getter, setter 자동 생성이 가능하다.
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
