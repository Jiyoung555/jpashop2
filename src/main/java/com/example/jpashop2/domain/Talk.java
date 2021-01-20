package com.example.jpashop2.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

//DB에서 TALK에 + MEMBER 붙여 보기
//
//SELECT *
//FROM TALK AS T /*TALK가 FK 가짐*/
//JOIN MEMBER AS M
//ON T.member_id = M.member_id;

@Entity @Getter @Setter
public class Talk {
    @Id
    @GeneratedValue
    @Column(name = "talk_id")
    private Long id;

    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime talkDate;
    @Column
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //양방향
    public void setMember(Member member) {
        this.member = member;
        member.getTalks().add(this);
    }

    //**생성 메소드
    public static Talk createTalk(String title, String content, Member member){
        Talk talk = new Talk();
        talk.setTitle(title);
        talk.setContent(content);
        talk.setMember(member);
        talk.setTalkDate(LocalDateTime.now());
        talk.setUpdatedDate(LocalDateTime.now());
        return talk;
    }


    public void rewrite(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedDate = LocalDateTime.now();
    }
}
