package com.example.jpashop2.api;


import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.Talk;
import com.example.jpashop2.dto.TalkForm;
import com.example.jpashop2.repository.TalkRepository;
import com.example.jpashop2.repository.UserRepository;
import com.example.jpashop2.service.TalkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TalkApiController {
    private final UserRepository userRepository;
    private final TalkService talkService;

    //talk 자유게시판 글생성
    @PostMapping("/api/talk")
    public Long create(@RequestBody TalkForm talkForm, HttpSession session){
        String loginEmail = (String) session.getAttribute("loginEmail"); //security로그인시 세션 등록해둠
        log.info("loginEmail 세션 : " + loginEmail);
        Member member = userRepository.findByEmail(loginEmail).orElse(null);
        log.info("member : " + member);

        //1. dto에서 entity화
        //Talk talk = talkForm.toTalk();//dto->entity
        //talk.setMember(member);

        //2. dto 칼럼들을 가지고 -> entity에 가서, entity 생성
        String title = talkForm.getTitle();
        String content = talkForm.getContent();
        Talk talk = Talk.createTalk(title, content, member);

        Talk saved = talkService.create(talk);
        return saved.getId();
    }

    //talk 자게 삭제
    @DeleteMapping("/api/talk/{id}")
    public Long destroy(@PathVariable Long id) {
        return talkService.destroy(id);
    }

    //talk 자게 수정
    @PutMapping("/api/talk/{id}")
    public Long update(@PathVariable Long id, @RequestBody TalkForm form) {
        Talk updated = talkService.update(id, form);
        return updated.getId();
    }
}
