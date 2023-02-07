package kr.spring.test.controller;
// 컨트롤러 테스트
import kr.spring.test.dto.TestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//일반적으로 @Controller를 적는다.
//아래에 있는 정보를 그대로 client에게 return
@RestController
public class TestController {

    //	옛날방식
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/test")
    public TestDto test() {
        TestDto dto = new TestDto(); // JSON 형식(키, 값 형식)으로 받아옴
        dto.setName("홍길동");
        dto.setAge(10);

        return dto;
    }
}
