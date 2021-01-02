package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //구분값 설정 //미입력 시, 그냥 클래스명으로 설정됨(Book)
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String type;

}