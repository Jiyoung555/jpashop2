package com.example.jpashop2.config;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository; //연습 위해 새로 만듦

    //로그인시 사용
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //파라미터 username => name, memberName으로 변수명 바꾸면 안 됨 (Override 한 거라서)
        Optional<Member> optionalUser = userRepository.findByName(username);

        return optionalUser
                .map(PrincipalDetails::new)// 입력받은 userame에 해당하는 사용자가 있다면, PrincipalDetails 객체를 생성한다.
                .orElse(null); // 없다면 null을 반환한다. (인증 실패)

        //에러 발생 : UserDetailsService returned null, which is an interface contract violation
        //이 메소드 리턴값이 null 라서 발생하는 거 같은데??
        //블로그 https://memostack.tistory.com/178


//        Member memberEntity = userRepository.findByName(username).orElse(null);
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(memberEntity.getRole()));
//        //return new Member(memberEntity.getName(), memberEntity.getPassword(), authorities);









//        Member optionalUser = userRepository.findByName(username).orElseThrow(
//                () -> new UsernameNotFoundException("User not exist with name : " + username)
//        );
//
//        return new PrincipalDetails(optionalUser);



    }

}
