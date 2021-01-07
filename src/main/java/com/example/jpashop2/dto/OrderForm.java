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

    //아래 체크박스 두개 안 됨..
    ArrayList<Long> cartIds;//cart -> checkbox

    ArrayList<Long> itemIds;//store -> checkbox로 여러개 주문시
    //테스트중..
//    public List<Item> toItemList(){
//        List<Item> itemList = new ArrayList<>();
//        for(int i = 0; i < itemIds.size(); i++){
//            Long id = itemIds.get(i);
//            Item item = itemService.findOne(id);
//            itemList.add(item);
//        }
//        return itemList;
//    }

    public Member toMember() {
        Member member = memberService.findOne(memberId);
        return member;
    }

//    public Item toItem(){
//        Item item = itemService.findOne(itemId);
//        return item;
//    }

    public OrderItem toOrderItem(){
        Item item = itemService.findOne(itemId);
        OrderItem orderItem = new OrderItem();
        orderItem.createOrderItem(item, orderPrice, count);
        //orderItem.setOrderPrice(orderPrice);
        //orderItem.setCount(count);
        return orderItem;
    }


}
