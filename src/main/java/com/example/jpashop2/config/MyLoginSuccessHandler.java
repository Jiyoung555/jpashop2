package com.example.jpashop2.config;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.repository.UserRepository;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//로그인 성공시 처리할 로직
@Slf4j
@RequiredArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

//    @Autowired
//    final UserRepository userRepository;
//    //final로 하니, SecurityConfig에서 new MyLoginSuccessHandler()할 때 에러남
//    //이거 사용하려면, SecurityConfig에서에서 new MyLoginSuccessHandler()에 파라미터 수정하기


    @Autowired
    MemberService memberService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        //인증 과정에서 생성 된 Authentication 객체

        HttpSession session = request.getSession();

        String email = authentication.getName(); //로그인 email 찾아옴
        log.info("로그인 email : " + email);
        session.setAttribute("loginEmail", email);

        Object principal = authentication.getPrincipal(); //PrincipalDetails 타입
        log.info("principal : " + principal);

        //**member 테이블의 orders, carts, talks를 못 가져옴.. (아마 lazy 때문..)
        //Optional<Member> member = userRepository.findByEmail(email);
        //log.info("member 찾았니 : " + member);
        //session.setAttribute("loginId", member.getId());
        //session.setAttribute("loginMember", member);

        response.sendRedirect("/login");
    }

}
