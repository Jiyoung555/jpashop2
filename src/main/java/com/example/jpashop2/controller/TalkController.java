package com.example.jpashop2.controller;

import com.example.jpashop2.domain.Talk;
import com.example.jpashop2.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TalkController {
    private final TalkRepository talkRepository;

    //Talk-자유게시판
    @GetMapping("/talk")
    public String talk(Model model){
        Iterable<Talk> talkList = talkRepository.findAll();
        model.addAttribute("talks", talkList);
        return "talks/talkList";
    }

    @GetMapping("/admin/talk")
    public String adminTalk(Model model){
        Iterable<Talk> talkList = talkRepository.findAll();
        model.addAttribute("talks", talkList);
        return "talks/talkList_admin";
    }

    @GetMapping("/talkForm")
    public String talkForm(){
        return "talks/talkForm";
    }

    @GetMapping("/talk/{id}")
    public String talkShow(@PathVariable Long id, Model model){
        Talk talk = talkRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        model.addAttribute("talk", talk);
        //model.addAttribute("comments", talk.getComments());

        return "talks/talkShow";
    }


    //TalkEdit-자게 수정 페이지
    @GetMapping("/talk/edit/{id}")
    public String talkEdit(@PathVariable Long id, Model model) {
        Talk target = talkRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );

        model.addAttribute("talk", target);
        return "talks/talkEdit";
    }
}
