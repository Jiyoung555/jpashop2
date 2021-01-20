package com.example.jpashop2.config;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//나중에 클래스명 바꿀 예정: CustomUserDetailService
//로그인시, 자동으로 UserDetailsService의 loadUserByUsername() 메소드를 호출한다
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository; //연습 위해 새로 만듦

    //로그인시 사용
    @Transactional //영속성을 위해 서비스 메소드는 트랜잭션으로 묶기.. //아니면 에러남 (failed to lazily initialize a collection of role)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //파라미터 username => name, memberName으로 변수명 바꾸면 안 됨 (Override 한 거라서)

//        Optional<Member> optionalUser = userRepository.findByEmail(username);
//        log.info("찾았니? " + optionalUser);

//        return optionalUser
//                .map(PrincipalDetails::new)// 입력받은 userame에 해당하는 사용자가 있다면, PrincipalDetails 객체를 생성한다.
//                .orElse(null); // 없다면 null을 반환한다. (인증 실패)
//
//        //블로그 https://memostack.tistory.com/178


        Member member = userRepository.findByEmail(username).orElse(null);
        log.info("로그인 시도 중인 회원 : " + member);

        if(member == null) {
            throw new UsernameNotFoundException(username);
        }
        return new PrincipalDetails(member);

    }

}
