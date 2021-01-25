package com.example.jpashop2.config;

import com.example.jpashop2.repository.UserRepository;
import com.example.jpashop2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration //테스트..
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    PrincipalDetailsService principalDetailsService;

//    @Autowired
//    UserRepository userRepository; //테스트..

    //암호화 방식 빈(Bean) 생성
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //비밀번호 암호화 //암호화 로직이 담긴 객체를 Bean으로 등록
    }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//        // 해당 요청은 로그인 인증 대상에서 제외
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // Spring Security로 인한 h2-console 화면 접근 차단 중지
                .headers().frameOptions().disable(); //Spring Security로 인한 X-Frame-Options 중지

        http.authorizeRequests()
                .antMatchers("/member/**").authenticated() //(단순)로그인 필요. 비로그인시, login 페이지로 이동
                .antMatchers("/admin/**").hasRole("ADMIN")
                //여기엔 ADMIN이라고 썼지만, DB 저장된 값은 반드시 ROLE_ADMIN이어야 함
                //스프링 시큐리티에서 권한 다룰 때, 자체적으로 PREFIX로 ROLE_을 추가함
                .antMatchers("/**").permitAll(); //아무나 허용

        http.formLogin() //폼 로그인만 되나..?
                .loginPage("/login")
                .loginProcessingUrl("/loginAuth") // 로그인이 실제 이루어지는 곳...
                .usernameParameter("email") //**
                //.passwordParameter("")
                .successHandler(new MyLoginSuccessHandler())
                //.successHandler(new MyLoginSuccessHandler(userRepository))
                //**userRepository를 파라미터 넣어야 -> MyLoginSuccessHandler에서 userRepository를 Autowired로 받아올 수 있음
                //**필요없어서, 파라미터 지금 없앴음
                //.defaultSuccessUrl("/cart") //로그인 성공시 이동할 페이지 (임시로 카트페이지)
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login") //로그아웃 성공시, 이동할 경로
                .invalidateHttpSession(true); //세션 삭제

        http.exceptionHandling()
                .accessDeniedPage("/denied"); //권한이 없는 사용자가 접근했을 경우 이동할 경로
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {//사용자 인증을 담당
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
        //principalDetailsService로 넘겨서, 로그인시 비밀번호 체크 로직 구현
    }


}