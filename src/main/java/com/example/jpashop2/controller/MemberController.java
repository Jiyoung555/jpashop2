package com.example.jpashop2.controller;
import com.example.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberRepository memberRepository;


    //로그인 성공시 (테스트..)
    @GetMapping("/main")
    public String index(){
        return "index";
    }



    //회원가입 form 화면
    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }

    /*
    //로그인 form 화면
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate(); //세션 삭제
        return "redirect:/sone"; //view 필요 없음
    }
*/
    //회원가입, 로그인 form 제출 -> api

}
