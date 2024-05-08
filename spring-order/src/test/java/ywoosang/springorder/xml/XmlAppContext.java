package ywoosang.springorder.xml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ywoosang.springorder.member.MemberService;
import ywoosang.springorder.member.MemberServiceImpl;

public class XmlAppContext {
    @Test
    void xmlAppContext() throws Exception {
        // xml 설정 파일을 이용해 ApplicationContext 를 생성한다.
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
