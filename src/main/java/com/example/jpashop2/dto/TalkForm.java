package com.example.jpashop2.dto;
import com.example.jpashop2.domain.Talk;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TalkForm {
    private String title;
    private String content;

//    public Talk toTalk() {
//        Talk talk = new Talk();
//        talk.setTitle(title);
//        talk.setContent(content);
//        talk.setTalkDate(LocalDateTime.now());//**
//        return talk;
//    }

    //Long memberId;

}
