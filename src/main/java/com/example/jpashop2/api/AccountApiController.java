package com.example.jpashop2.api;

import com.example.jpashop2.domain.Address;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.dto.MemberForm;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountApiController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; //시큐리티에서 빈(Bean). SecurityConfig에 생성해뒀음 //회원가입시 사용

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //아이디 중복체크 //ajax 통신이니, ApiController로 와야함 (@RestController)
    //GET 방식은 ajax로 받을 때, RequestBody가 없음. 걍 파라미터로 받기
    @GetMapping("/idCheck")
    public String id_check(String email) {
        List<Member> memberList = memberRepository.findByEmail(email);
        System.out.println("몇 개 있나요? " + memberList.size());

        if (memberList.size() == 0) { //가입한 적 없으면
            return "noMember";
        } else { //가입한 적 있으면
            return "yesMember";
        }
    }

    //회원가입 form 제출
    @PostMapping("/api/signup")
    public Long signUpSubmit(@RequestBody MemberForm memberForm) {
        System.out.println("memberForm : " + memberForm);

        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword()));
        //비밀번호 암호화돼서 DB 저장됨!

        Member member = memberForm.toMember();
        Address address = memberForm.toAddress();
        member.setAddress(address);

        System.out.println("member : " + member);

        return memberService.join(member);
        //service 안 거치고, 바로 repository 직행하면 에러남
        //에러: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
        //return memberRepository.save(member); //이렇게 리파지터리 직행 x
    }


//    //로그인 form 제출 (js 없이로 수정중..)
//    @PostMapping("/api/login")
//    public String loginAuth(@RequestBody MemberForm memberForm, HttpSession httpSession) {
//        log.info("memberForm : " + memberForm);
//        String result = memberService.login(memberForm, httpSession);
//        return result;
//    }







}
