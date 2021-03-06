package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") //구분값 설정 //미입력 시, 그냥 클래스명으로 설정됨(Movie)
@Getter
@Setter
public class Movie extends Item {
    private String director;
    private String actor;
}