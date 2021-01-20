package com.example.jpashop2.service;

import com.example.jpashop2.domain.Talk;
import com.example.jpashop2.dto.TalkForm;
import com.example.jpashop2.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TalkService {
    private final TalkRepository talkRepository;

    //게시글 생성 (talk 자유게시판)
    @Transactional
    public Talk create(Talk talk) {
        Talk saved = talkRepository.save(talk);
        return saved;
    }

//    @Transactional
//    public Talk create(TalkForm form) { // 0. form -> js -> dto
//        // 1. dto 로그
//        log.info("form : " + form.toString());
//        // 2. dto -> entity
//        Talk talk = form.toEntity();
//        // 3. db 저장
//        Talk saved = talkRepository.save(talk);
//        return saved;
//    }

    //게시글 수정 -> JPQL문 말고, 엔티티를 수정해서, 다시 저장
    @Transactional
    public Talk update(Long id, TalkForm form) {
        Talk target = talkRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        log.info("target: " + target.toString());

        target.rewrite(form.getTitle(), form.getContent());
        Talk updated = talkRepository.save(target);

        log.info("updated: " + updated.toString());
        return updated;
    }

    //게시글 삭제 (talk 자유게시판)
    @Transactional
    public Long destroy(Long id) {
        Talk target = talkRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        talkRepository.delete(target);
        log.info(target.toString() + "이 삭제 되었습니다");
        return target.getId();
    }



}
