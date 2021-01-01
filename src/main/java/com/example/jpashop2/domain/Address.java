package com.example.jpashop2.domain;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;

@Embeddable
//Entity 아님
//엔티티는 아니지만, 한 엔티티에 들어가진다 (내장)
//이 Address 클래스가 하나의 객체처럼 묶여서, Member 엔티티에 들어가짐
@Getter
@Setter
public class Address {
    //private String city;
    //private String street;
    private String zipcode;
    private String addr1;
    private String addr2;
}
