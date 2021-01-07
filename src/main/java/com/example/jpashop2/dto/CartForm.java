package com.example.jpashop2.dto;
import com.example.jpashop2.domain.CartItem;
import com.example.jpashop2.domain.Item;
import com.example.jpashop2.domain.Member;
import com.example.jpashop2.service.ItemService;
import com.example.jpashop2.service.MemberService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartForm {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ItemService itemService;

    Long itemId;
    String itemName;
    int cartPrice;
    int cartCount;
    Long memberId;

    public Member toMember() {
        Member member = memberService.findOne(memberId);
        return member;
    }

    public CartItem toCartItem(){
        Item item = itemService.findOne(itemId);
        CartItem cartItem = new CartItem();
        cartItem.createCartItem(item, cartPrice, cartCount);
        return cartItem;
    }


}