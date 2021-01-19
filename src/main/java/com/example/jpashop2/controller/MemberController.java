package com.example.jpashop2.controller;
import com.example.jpashop2.dto.MemberForm;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    //private final MemberRepository memberRepository;
    private final MemberService memberService;

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

    //회원가입, 로그인 form 제출 -> api

    //로그인 form 제출 -> js없이 테스트중..
    @PostMapping("/loginAuth")
    public void loginAuth(MemberForm memberForm, HttpSession httpSession, Model model) {
        log.info("memberForm : " + memberForm);
        String result = memberService.login(memberForm, httpSession, model);
        //return result;
    }

}
