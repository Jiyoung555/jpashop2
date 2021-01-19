package com.example.jpashop2.dto;

import com.example.jpashop2.domain.Item;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.domain.OrderItem;
import com.example.jpashop2.repository.MemberRepository;
import com.example.jpashop2.service.ItemService;
import com.example.jpashop2.service.MemberService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    Long memberId;

    //1. cartList에서 체크한 거
    ArrayList<Long> cartIdArr;

    //2. store에서 체크한 거
    ArrayList<Long> itemIdArr;

    public Member toMember() {
        Member member = memberService.findOne(memberId);
        return member;
    }

    public OrderItem toOrderItem(){
        Item item = itemService.findOne(itemId);
        OrderItem orderItem = new OrderItem();
        orderItem.createOrderItem(item, orderPrice, count);
        //orderItem.setOrderPrice(orderPrice);
        //orderItem.setCount(count);
        return orderItem;
    }
}
