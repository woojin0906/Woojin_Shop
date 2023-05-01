package kr.spring.member.controller;

import jakarta.validation.Valid;
import kr.spring.member.dto.MemberFormDto;
import kr.spring.member.entity.Member;
import kr.spring.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    // restfull 방식. 이름이 똑같아도 어노테이션이 다르니까 ㄱㅊ다
    // 하지만 오버로딩이 안 되면 에러 뜹니다 오버로딩 해줘야 함!
    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, // @Valid 자동으로 체크해주는 어노테이션
                             BindingResult bindingResult, Model model) {  // 에러에 대한 결과를 받아옴
        // annotation에서 에러가 있으면 다시 memberform으로 돌아간다 -> 바인딩 에러 시 처리
        if(bindingResult.hasErrors()) {  // bindingResult가 하나라도 에러인 경우
            return "member/memberForm";
        }
        try { //회원가입을 처리하는 구문
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) { //회원가입 처리 시 문제가 생기면 에러메세지 띄우기
            model.addAttribute("errorMessage", e.getMessage());
            //문제가 있으면 회원가입으로 돌아감
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "member/memberLogin";
   }

   @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 패스워드가 잘못되었습니다.");

        return "member/memberLogin"; //에러가 발생하면서 다시 로그인 폼으로 돌아간다
   }
}
