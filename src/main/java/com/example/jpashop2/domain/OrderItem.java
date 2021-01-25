package com.example.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

//중간 테이블
@Entity
@Getter
@Setter //@세터 만듦 (학습용)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //N:1 //주문(*) - (1)아이템
    @JoinColumn(name = "item_id") //한 item을 붙인다. item 클래스의 PK를 통해 //그 PK가 여기서는 FK가 되고, 그 FK명을 지금 정해줌
    private Item item;

    // @ManyToOne과 @JoinColumn은 한 셋트! //@JoinColumn을 갖는 엔티티가 "연관 관계의 주인"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")  //order 클래스를 붙인다. order 클래스의 PK를 통해 //그 PK가 여기서는 FK가 되고, 그 FK명을 지금 정해줌
    private Order order;

    private int orderPrice; //가격
    private int count; //수량
    private int totalPrice; //가격 x 수량

    //**주문 상품 - 생성 & 초기 셋팅
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();//OrderItem 객체 생성

        orderItem.setItem(item);//객체 초기 셋팅
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        orderItem.setTotalPrice(orderPrice * count);

        item.removeStock(count);//주문 상품 생성했으니 -> Item 재고 수량 빼기

        return orderItem;
    }

     //**주문 상품 - 취소
    public void cancel() {
        getItem().addStock(count);//주문 상품 취소했으니 -> Item 재고 수량 다시 복구시키기
    }


    //**주문 상품의 총 가격 (@@내가 개수로 바꿈)
    public int getTotalPrice() {
        return getOrderPrice() * getCount(); //주문 가격 x 개수
    }

}
