package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
//테이블 이름을 orders로 바꿈
//sql 키워드 중, order라는 게 존재함. 테이블명으로 order 사용하지 말 것
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //@ManyToOne과 @JoinColumn은 한 셋트! //@JoinColumn을 갖는 엔티티가 "연관 관계의 주인"
    @ManyToOne(fetch = FetchType.LAZY) //여러개의 주문 - 하나의 멤버 (테이블 연결)
    @JoinColumn(name = "member_id") //member 클래스를 붙인다. member 클래스의 PK를 통해 //그 PK가 여기서는 FK가 되고, 그 FK명을 지금 정해줌
    private Member member;

    //지금은 관계 연결만 돼있음. 진짜 누구 member인지는 메소드 통해 해야됨
    //setMember 메소드 추가!
    public void setMember(Member member) {//본 클래스인 Order에, 진짜로 Member 추가하는 메소드
        this.member = member;
        member.getOrders().add(this);//양방향 편리를 위해, 여기에 Member 추가하면 -> 반대쪽인 Member 클래스에도, 본 Order를 연결(this)
    }

    //========================

    //ORDER(1) - (*)ITEM의 중간 테이블
    //한 주문에 - 연결된 상품들 수량, 가격 엔티티 연결
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    //[앞에 설명 보류 부분]양방향 관계이므로, OrderItem 테이블의 order가 주인임을 선언 //1:N 관계에서, N에서 수정할 수 있나봄..
    //주인은 캐스캐이드 설정: 본 클래스인 Order 삭제시, 내부의 orderItems들도 삭제해줘

    //**여기도 바꾸지만, 저기도 바꾸기 (하나의 메소드로)
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this); //OrderItem 테이블에 @setter 만들기 //this = 이 Order
    }

    //==========================

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //1:1 관계 연결
    @JoinColumn(name = "delivery_id") //delivery 클래스 붙임. delivery 클래스의 PK를 통해 //그 PK가 여기선 FK가 되고, 그 FK명을 지금 정함
    private Delivery delivery; //@JoinColumn을 갖는 엔티티가 "연관 관계의 주인"
    //주인은 캐스캐이드 설정: 본 클래스인 Order 삭제시, 내부의 delivery도 삭제해줘

    //**여기도 바꾸지만, 저기도 바꾸기 (하나의 메소드로)
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==========================

    //주문한 시각
    private LocalDateTime orderDate;

    //주문 상태 //enum타입
    @Enumerated(EnumType.STRING) //이거 추가시, DB drop 또 해야됨
    private OrderStatus status;


    //==================

    //**생성 메소드
    public static Order createOrder(
            Member member,
            Delivery delivery,
            List<OrderItem> orderItems) { // ... = [] 배열 //OrderItem... orderItems에서 내가 바꿈**
        // 주문 객체 만들고, 초기 셋팅
        Order order = new Order(); //주문 객체 만들고

        order.setMember(member);//회원
        order.setDelivery(delivery);//배송


        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);//주문 상품들
        }

        order.setStatus(OrderStatus.ORDERED);//주문 상태(**IN_CART에서 내가 바꿈)
        order.setOrderDate(LocalDateTime.now());//주문 생성시간

        return order;
    }



    //**주문 취소 메소드
    public void cancel() {
        //배송 완료 상태시, 주문 취소 불가
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("배송 완료 상품은 취소할 수 없습니다.");
        }

        //(배송 완료된 게 아니라면) 주문 취소시키기
        this.setStatus(OrderStatus.CANCELED);

        //Order만 취소시키는 게 아니라, OrderItem들도 모두 연달아서 취소
        //주문 취소후 -> 주문 상품들도 모두 취소
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }



    //**전체 주문 가격 구하기
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice(); //아이템의 각 가격을 더하기
        }

        //[참고] Java 8 Stream으로 작성 가능
        //int totalPrice = orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();

        return totalPrice;
    }


}
