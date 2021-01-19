package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter //@@setter 만들기
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //1:1
    //본 Delivery 클래스는 주인이 아님 (order 클래스가 주인임. @JoinColumn FK를 가짐으로)
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) //FK는 주인 Order에 추가해둔 delivery에서 관리
    private Order order; //여기서 값이 바뀌어도 DB는 변경되지 않음 (여긴 주인 아니니까)

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //String이 안전 //기본 타입 ORDINAL은 숫자라, 새롭게 추가되는 경우 위험
    private DeliveryStatus status; //DeliveryStatus 이넘 //READY, COMP
}
