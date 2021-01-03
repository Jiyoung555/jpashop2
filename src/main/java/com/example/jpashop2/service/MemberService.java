package com.example.jpashop2.service;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.dto.MemberForm;
import com.example.jpashop2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

/*
@Transactional
-메소드 안의 여러 코드가 있는데, 트랜잭션으로 묶어야 함
-여러 코드 중, 한 번이라도 실패하면, 다 무르고 돌아가게끔
-DB 수정하다가 에러나면, 다 되돌려야죠
-성공시 commit, 실패시 rollback
*/
@Slf4j
@Service
@Transactional(readOnly = true) //모든 public 메소드에 적용시키기 //import springframework
@RequiredArgsConstructor //final 필드는 생성자를 만들어, 자동으로 객체 얻어오기
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입(저장)
    @Transactional//본 메소드는 DB를 "조회"만 하는 읽기 전용 아니니까, readOnly = false 적용
    public Long join(Member member) {
        validateDuplicateMember(member); //아래 '회원 가입 여부 확인' 메소드 실행
        return memberRepository.save(member);

        //여기에 중복불가 조건 메소드 있지만
        //DB에도 조건 걸어서 한번 더 검증시키기 -> Member 테이블의 name 컬럼에 unique 조건 걸기
    }


    //회원 가입 여부 확인 메소드 (중복 회원 검증)
    private void validateDuplicateMember(Member member) {
        // 이름으로 회원을 찾고
        List<Member> findMembers = memberRepository.findByNames(member.getName());

        //이미 회원이면 (리턴값이 empty 하지 않다면)-> 에러 발생시키기
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //=============================================

    //회원 전체 조회
    //@Transactional(readOnly = true)//위로 뺌
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 한 명 조회
    //@Transactional(readOnly = true)//위로 뺌
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


    //==========================================

    //로그인
    //@Transactional(readOnly = true)//위로 뺌
    public String login(MemberForm form, HttpSession httpSession) {//1.form -> dto
        //2.해당 회원 있는지
        List<Member> memberList = memberRepository.findByEmail(form.getEmail());
        System.out.println("memberList 길이 : " + memberList.size());

        //3-1.회원이면
        if (memberList.size() != 0) {
            Member member = memberList.get(0);
            System.out.println("member : " + member);

            if (form.getPassword().equals(member.getPassword())) {//패스워드 일치시
                //세션 등록
                httpSession.setAttribute("loginEmail", member.getEmail());
                httpSession.setAttribute("loginId", member.getId());
                httpSession.setAttribute("loginMember", member);
                log.info("loginMember : " + member);

                //어드민 계정이면, 세션 또 등록
                if (member.getRole().equals("admin")) {
                    httpSession.setAttribute("admin", member.getRole());
                }
                return "loginSuccess";

            } else { //패스워드 틀리면
                return "wrongPwd";
            }

        } else {//비회원이면
            return "noMember";
        }//end if~else

    }//end login-method





}




