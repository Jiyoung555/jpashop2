package com.example.jpashop2.dto;

import com.example.jpashop2.domain.*;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.service.ItemService;
import com.example.jpashop2.service.MemberService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;

    Long itemId;
    String itemName;
    int orderPrice;
    int count;
    //Long memberId;
    //String memberEmail;//시큐리티

    //1. cartList에서 체크한 거
    ArrayList<Long> cartIdArr;

    //2. store에서 체크한 거
    ArrayList<Long> itemIdArr;


    public OrderItem toOrderItem(){
        Item item = itemService.findOne(itemId);
        OrderItem orderItem = new OrderItem();
        orderItem.createOrderItem(item, orderPrice, count);
        //orderItem.setOrderPrice(orderPrice);
        //orderItem.setCount(count);
        return orderItem;
    }




    //Payment 객체
    String imp_uid;//고유ID
    String merchant_uid;//상점 거래 ID
    Long apply_num;//카드 승인번호
    int amount;//결제 금액

    public Payment toPayment() {
        Payment payment = new Payment();

        payment.setImp_uid(imp_uid);
        payment.setMerchant_uid(merchant_uid);
        payment.setApply_num(apply_num);
        payment.setAmount(amount);

        payment.setStatus(OrderStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());

        return payment;
    }
}

//    public Member toMember() {
//        //Member member = memberService.findOne(memberId);
//        Member member = memberService.findByEmail(memberEmail);
//        return member;
//    }