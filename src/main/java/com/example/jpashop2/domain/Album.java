package com.example.jpashop2.domain;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //구분값 설정 //미입력 시, 그냥 클래스명으로 설정됨(Album)
@Getter
//@Setter
public class Album extends Item {
    private String artist;
    private String etc;
}
